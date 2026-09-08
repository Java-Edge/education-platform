package com.javaedge.growth.aggregate;

import com.javaedge.growth.entity.SignRecord;
import com.javaedge.growth.event.DomainEvent;
import com.javaedge.growth.event.MemberLevelUpgradedEvent;
import com.javaedge.growth.event.MemberSignedEvent;
import com.javaedge.growth.event.PointsEarnedEvent;
import com.javaedge.growth.exception.AlreadySignedTodayException;
import com.javaedge.growth.service.SignRewardPolicy;
import com.javaedge.growth.valueobject.MemberId;
import com.javaedge.growth.valueobject.MemberLevel;
import com.javaedge.growth.valueobject.Points;
import com.javaedge.growth.valueobject.SignDate;
import com.javaedge.growth.valueobject.SignReward;
import com.javaedge.growth.model.CourseCompletion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员成长聚合根（Aggregate Root）。
 *
 * <p>聚合根定义了<b>一致性边界</b>：会员的积分、等级、本月签到记录必须作为一个整体保持一致，
 * 外部只能通过聚合根的方法修改状态，不能直接改里面的字段。这是 DDD 防止"对象图被随意篡改"的核心机制。
 *
 * <h3>不变量（Invariants）</h3>
 * <ul>
 *   <li>积分不可为负；</li>
 *   <li>同一自然日不可重复签到；</li>
 *   <li>等级由累计积分唯一推导，永远跟着积分走。</li>
 * </ul>
 *
 * <p>聚合根<b>不触碰任何基础设施</b>：它不知道数据库、不知道规则引擎、不知道 MQ。
 * 签到奖励怎么算，委托给 {@link SignRewardPolicy}（可插拔）；签到产生什么副作用，
 * 以领域事件形式返回，由应用层去发布。这正是"领域层零框架依赖"的体现。
 */
public class MemberGrowth {

    private final MemberId memberId;
    private Points totalPoints;
    private MemberLevel level;
    /** 当前已加载（本月）的签到记录。 */
    private final List<SignRecord> signRecords;
    /** 本次新增、尚未持久化的签到记录，由仓储 save 时落库。 */
    private final List<SignRecord> pendingSignRecords;

    public MemberGrowth(MemberId memberId, Points totalPoints, MemberLevel level, List<SignRecord> signRecords) {
        this.memberId = memberId;
        this.totalPoints = totalPoints;
        this.level = level;
        this.signRecords = new ArrayList<>(signRecords);
        this.pendingSignRecords = new ArrayList<>();
    }

    /** 全新会员：0 积分、1 级、无签到。 */
    public static MemberGrowth create(MemberId memberId) {
        return new MemberGrowth(memberId, Points.of(0), MemberLevel.initial(), new ArrayList<>());
    }

    public MemberId memberId() {
        return memberId;
    }

    public Points totalPoints() {
        return totalPoints;
    }

    public MemberLevel level() {
        return level;
    }

    public List<SignRecord> signRecords() {
        return List.copyOf(signRecords);
    }

    /** 今日是否已签到（含本次会话内刚加的待持久化记录）。 */
    public boolean hasSignedOn(SignDate date) {
        return signRecords.stream().anyMatch(r -> r.signDate().equals(date))
                || pendingSignRecords.stream().anyMatch(r -> r.signDate().equals(date));
    }

    /**
     * 签到——聚合根的核心行为。
     *
     * @param policy 奖励策略（可插拔：配置 / Drools / ...）
     * @param today  签到日期（通常由应用层传入 Clock，便于测试；此处用 SignDate）
     * @return 本次签到的产出（奖励 + 连续天数 + 领域事件），不含任何基础设施副作用
     */
    public SignOutcome signToday(SignRewardPolicy policy, SignDate today) {
        if (hasSignedOn(today)) {
            throw new AlreadySignedTodayException();
        }

        // 1) 记录签到
        SignRecord record = new SignRecord(memberId, today);
        signRecords.add(record);
        pendingSignRecords.add(record);

        // 2) 计算连续签到天数（基于已加载的本月记录推导，不依赖外部查询）
        int continuousDays = computeContinuousDays(today);

        // 3) 委托领域服务计算奖励规则（策略可插拔）
        SignReward reward = policy.rewardFor(continuousDays);

        // 4) 累加积分，并据新积分重算等级（等级永远由积分推导）
        totalPoints = totalPoints.add(reward.total());
        MemberLevel fromLevel = level;
        level = level.upgradeIfNeeded(totalPoints);

        // 5) 产出领域事件，交给应用层发布
        List<DomainEvent> events = new ArrayList<>();
        events.add(MemberSignedEvent.of(memberId, today, Points.of(reward.base())));
        events.add(PointsEarnedEvent.of(memberId, reward.total(), "SIGN"));
        if (level.level() > fromLevel.level()) {
            events.add(MemberLevelUpgradedEvent.of(memberId, fromLevel, level));
        }
        return new SignOutcome(reward, continuousDays, events);
    }

    /**
     * 课程完成奖励——演示防腐层（ACL）的消费端。
     *
     * <p>聚合根只认识本域的 {@link CourseCompletion}（由 {@code CourseCompletionGateway} 翻译得到），
     * 不接触课程中心的任何外部模型。发放积分时复用"累加 → 重算等级 → 产出事件"的统一路径，
     * 保证所有积分变动都走同一致性边界。
     *
     * @param completion 本域认识的"课程完成"本地模型
     * @return 本次发放产出（发放分 + 领域事件）
     */
    public AwardOutcome awardCoursePoints(CourseCompletion completion) {
        if (!completion.completed()) {
            return new AwardOutcome(Points.of(0), List.of());
        }
        Points granted = Points.of(completion.grantedPoints());
        totalPoints = totalPoints.add(granted);
        MemberLevel fromLevel = level;
        level = level.upgradeIfNeeded(totalPoints);

        List<DomainEvent> events = new ArrayList<>();
        events.add(PointsEarnedEvent.of(memberId, granted, "COURSE"));
        if (level.level() > fromLevel.level()) {
            events.add(MemberLevelUpgradedEvent.of(memberId, fromLevel, level));
        }
        return new AwardOutcome(granted, events);
    }

    /**
     * 基于已加载的签到记录，从 today 往前逐日检查，推算连续天数。
     * 不依赖记录顺序（用 contains 语义），因此重建聚合时无需排序。
     */
    int computeContinuousDays(SignDate today) {
        int continuous = 0;
        SignDate cursor = today;
        boolean found;
        do {
            SignDate finalCursor = cursor;
            found = signRecords.stream().anyMatch(r -> r.signDate().equals(finalCursor));
            if (found) {
                continuous++;
                cursor = previousDay(cursor);
            }
        } while (found);
        return continuous;
    }

    private SignDate previousDay(SignDate d) {
        LocalDate ld = d.toLocalDate().minusDays(1);
        return SignDate.of(ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth());
    }

    /** 取出并清空本次新增的签到记录（供仓储落库）。 */
    public List<SignRecord> drainPendingSignRecords() {
        List<SignRecord> drained = new ArrayList<>(pendingSignRecords);
        pendingSignRecords.clear();
        return drained;
    }

    /** 一致性自检（示例：积分不可为负）。 */
    public void assertInvariants() {
        if (totalPoints.amount() < 0) {
            throw new IllegalStateException("积分不可为负");
        }
    }
}
