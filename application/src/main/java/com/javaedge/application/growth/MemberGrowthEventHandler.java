package com.javaedge.application.growth;

import com.javaedge.growth.event.MemberLevelUpgradedEvent;
import com.javaedge.growth.event.MemberSignedEvent;
import com.javaedge.growth.event.PointsEarnedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 领域事件订阅者（Application Event Handler）。
 *
 * <p>聚合根在 {@code signToday}/{@code awardCoursePoints} 中只<b>产出</b>领域事件、不产生副作用；
 * 真正的副作用（记日志、推通知、写读模型、发 MQ）由这里的 {@code @EventListener} 处理。
 * 这样聚合保持纯净、可单测，且事件天然可审计、可重放、可异步化。
 *
 * <p>示例中以日志代表"后续动作"；生产中可替换为推送消息队列或更新 CQRS 读模型。
 */
@Slf4j
@Component
public class MemberGrowthEventHandler {

    @EventListener
    public void onMemberSigned(MemberSignedEvent event) {
        log.info("[领域事件] 会员 {} 于 {} 完成签到，基础分 +{}",
                event.memberId().value(), event.date(), event.basePoints().amount());
    }

    @EventListener
    public void onPointsEarned(PointsEarnedEvent event) {
        log.info("[领域事件] 会员 {} 获得积分 +{}（来源：{}）",
                event.memberId().value(), event.amount().amount(), event.source());
    }

    @EventListener
    public void onLevelUpgraded(MemberLevelUpgradedEvent event) {
        log.info("[领域事件] 会员 {} 等级提升：{} 级 -> {} 级",
                event.memberId().value(), event.from().level(), event.to().level());
    }
}
