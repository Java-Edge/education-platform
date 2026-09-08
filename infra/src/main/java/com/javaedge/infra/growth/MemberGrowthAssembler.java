package com.javaedge.infra.growth;

import com.javaedge.common.enums.UserSignTypeEnums;
import com.javaedge.growth.entity.SignRecord;
import com.javaedge.growth.factory.MemberGrowthFactory;
import com.javaedge.growth.valueobject.MemberId;
import com.javaedge.growth.valueobject.MemberLevel;
import com.javaedge.growth.valueobject.Points;
import com.javaedge.growth.valueobject.SignDate;
import com.javaedge.growth.valueobject.SignReward;
import com.javaedge.infra.mysql.po.growth.IntegralLogPO;
import com.javaedge.infra.mysql.po.growth.MemberAccountPO;
import com.javaedge.infra.mysql.po.growth.SignRecordPO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员成长装配器（Assembler）：聚合根/值对象 与 持久化对象（PO）之间的双向转换。
 *
 * <p>装配器是 DDD 基础设施层的关键角色：它把"领域对象"与"数据库行"解耦，
 * 让领域层完全不知道 MyBatis-Plus / 表结构。转换规则集中在此处，便于审计与单测。
 *
 * <h3>关键约定</h3>
 * <ul>
 *   <li>等级（level）不落库，由 {@code MemberLevel.fromPoints} 从积分纯函数推导；</li>
 *   <li>签到流水按"基础分 / 连续奖励分"拆分为两条，对应 {@code UserSignTypeEnums}；</li>
 *   <li>重建聚合时仅加载当月签到记录，保证连续天数计算的一致性边界。</li>
 * </ul>
 */
public final class MemberGrowthAssembler {

    private MemberGrowthAssembler() {
    }

    /** PO(积分账户) + 当月签到记录PO -> 领域聚合根。 */
    public static com.javaedge.growth.aggregate.MemberGrowth toAggregate(MemberAccountPO account,
                                                                         List<SignRecordPO> signPORecords) {
        MemberId memberId = MemberId.of(account.getUserId());
        int totalPoints = account.getIntegral() == null ? 0 : account.getIntegral();
        // 等级由积分纯函数推导，不依赖存储
        MemberLevel level = MemberLevel.fromPoints(Points.of(totalPoints));
        List<SignRecord> records = new ArrayList<>(signPORecords.size());
        for (SignRecordPO po : signPORecords) {
            records.add(toSignRecordDomain(po));
        }
        return MemberGrowthFactory.reconstitute(memberId, totalPoints, level.level(), records);
    }

    public static SignRecord toSignRecordDomain(SignRecordPO po) {
        return new SignRecord(MemberId.of(po.getUserId()), SignDate.of(po.getYear(), po.getMonth(), po.getDay()));
    }

    public static MemberAccountPO toAccountPO(com.javaedge.growth.aggregate.MemberGrowth growth) {
        MemberAccountPO po = new MemberAccountPO();
        po.setUserId(growth.memberId().value());
        po.setIntegral(growth.totalPoints().amount());
        po.setCreateTime(LocalDateTime.now());
        return po;
    }

    public static SignRecordPO toSignRecordPO(SignRecord record) {
        SignRecordPO po = new SignRecordPO();
        po.setUserId(record.memberId().value());
        po.setYear(record.signDate().year());
        po.setMonth(record.signDate().month());
        po.setDay(record.signDate().day());
        return po;
    }

    /**
     * 由本次签到奖励产出积分流水：
     * 基础分 -> {@code SIGN_NORMAL_INTEGRAL}(1)，连续奖励分 -> {@code SIGN_CONTINOUS_INTEGRAL}(2)。
     */
    public static List<IntegralLogPO> toIntegralLogs(MemberId memberId, SignReward reward) {
        List<IntegralLogPO> logs = new ArrayList<>(2);
        if (reward.base() > 0) {
            logs.add(buildLog(memberId.value(), UserSignTypeEnums.SIGN_NORMAL_INTEGRAL.getType(), reward.base()));
        }
        if (reward.bonus() > 0) {
            logs.add(buildLog(memberId.value(), UserSignTypeEnums.SIGN_CONTINOUS_INTEGRAL.getType(), reward.bonus()));
        }
        return logs;
    }

    private static IntegralLogPO buildLog(Integer userId, int type, int integral) {
        IntegralLogPO po = new IntegralLogPO();
        po.setUserId(userId);
        po.setIntegralType(type);
        po.setIntegral(integral);
        po.setCreateTime(LocalDateTime.now());
        return po;
    }
}
