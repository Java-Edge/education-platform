package com.javaedge.application.growth;

import com.javaedge.common.enums.UserSignTypeEnums;
import com.javaedge.growth.aggregate.AwardOutcome;
import com.javaedge.growth.aggregate.MemberGrowth;
import com.javaedge.growth.aggregate.SignOutcome;
import com.javaedge.growth.event.DomainEvent;
import com.javaedge.growth.gateway.CourseCompletionGateway;
import com.javaedge.growth.repository.MemberGrowthRepository;
import com.javaedge.growth.service.SignRewardPolicy;
import com.javaedge.growth.valueobject.MemberId;
import com.javaedge.growth.valueobject.SignDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员成长应用服务（Application Service）。
 *
 * <p>应用服务是"用例"的编排者，它<b>不含业务规则</b>，只负责：
 * <ol>
 *   <li>取聚合（从仓储加载，或新建）；</li>
 *   <li>调用聚合根的业务行为（真正的不变量校验在聚合内）；</li>
 *   <li>通过仓储持久化；</li>
 *   <li>发布聚合产生的领域事件；</li>
 *   <li>把结果投影为 DTO 返回。</li>
 * </ol>
 * 事务边界在此处（{@code @Transactional}），一次用例 = 一个事务。
 *
 * <p>注意依赖方向：本类只依赖<b>领域层接口</b>（{@code MemberGrowthRepository}/{@code SignRewardPolicy}/
 * {@code CourseCompletionGateway}），具体实现在 infra 模块、由 Spring 在启动期注入。
 * 这正是"内层不依赖外层"的 DDD 依赖倒置。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SignApplicationService {

    private final MemberGrowthRepository memberGrowthRepository;
    private final SignRewardPolicy signRewardPolicy;
    private final CourseCompletionGateway courseCompletionGateway;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 签到用例。
     */
    @Transactional(rollbackFor = Exception.class)
    public SignResultDTO sign(SignCommand command) {
        MemberId memberId = MemberId.of(command.userId());
        MemberGrowth growth = memberGrowthRepository.findById(memberId)
                .orElseGet(() -> MemberGrowth.create(memberId));

        SignOutcome outcome = growth.signToday(signRewardPolicy, SignDate.today());
        memberGrowthRepository.save(growth, outcome);
        publish(outcome.events());

        return new SignResultDTO(
                memberId.value(),
                growth.totalPoints().amount(),
                growth.level().level(),
                outcome.continuousDays(),
                outcome.reward().base(),
                outcome.reward().bonus(),
                outcome.reward().description(),
                eventTypes(outcome.events()));
    }

    /**
     * 课程完成奖励用例——防腐层（ACL）端到端打通。
     *
     * <p>经 {@code CourseCompletionGateway} 向课程中心查询完成情况；网关内部把外部模型翻译为本域
     * {@code CourseCompletion}，聚合根只消费本域模型、完全不感知外部系统。若完成则发放积分并持久化。
     */
    @Transactional(rollbackFor = Exception.class)
    public MemberGrowthDTO completeCourse(CompleteCourseCommand command) {
        MemberId memberId = MemberId.of(command.userId());
        MemberGrowth growth = memberGrowthRepository.findById(memberId)
                .orElseGet(() -> MemberGrowth.create(memberId));

        com.javaedge.growth.model.CourseCompletion completion =
                courseCompletionGateway.fetchCompletion(memberId, command.courseCode());
        if (completion.completed()) {
            AwardOutcome outcome = growth.awardCoursePoints(completion);
            memberGrowthRepository.saveAward(growth, outcome, UserSignTypeEnums.COURSE_COMPLETION_INTEGRAL.getType());
            publish(outcome.events());
        }
        return toDTO(growth);
    }

    /**
     * 查询会员成长快照。
     */
    public MemberGrowthDTO getGrowth(Integer userId) {
        MemberId memberId = MemberId.of(userId);
        MemberGrowth growth = memberGrowthRepository.findById(memberId)
                .orElseGet(() -> MemberGrowth.create(memberId));
        return toDTO(growth);
    }

    private MemberGrowthDTO toDTO(MemberGrowth growth) {
        return new MemberGrowthDTO(
                growth.memberId().value(),
                growth.totalPoints().amount(),
                growth.level().level(),
                growth.signRecords().size());
    }

    private void publish(List<DomainEvent> events) {
        for (DomainEvent event : events) {
            eventPublisher.publishEvent(event);
        }
    }

    private List<String> eventTypes(List<DomainEvent> events) {
        List<String> types = new ArrayList<>(events.size());
        for (DomainEvent e : events) {
            types.add(e.getClass().getSimpleName());
        }
        return types;
    }
}
