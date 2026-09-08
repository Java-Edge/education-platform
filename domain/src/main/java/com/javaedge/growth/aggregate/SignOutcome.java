package com.javaedge.growth.aggregate;

import com.javaedge.growth.event.DomainEvent;
import com.javaedge.growth.valueobject.SignReward;

import java.util.List;

/**
 * 签到产出值对象：聚合根 {@code signToday} 的返回值。
 *
 * <p>把"奖励、连续天数、领域事件"打包成一个不可变结果，应用层据此：
 * <ul>
 *   <li>用 {@link #reward()} 生成积分流水并落库；</li>
 *   <li>用 {@link #events()} 发布领域事件；</li>
 *   <li>用 {@link #continuousDays()} 组装响应。</li>
 * </ul>
 */
public record SignOutcome(SignReward reward, int continuousDays, List<DomainEvent> events) {
}
