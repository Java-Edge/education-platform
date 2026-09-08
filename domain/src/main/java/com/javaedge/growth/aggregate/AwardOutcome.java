package com.javaedge.growth.aggregate;

import com.javaedge.growth.event.DomainEvent;
import com.javaedge.growth.valueobject.Points;

import java.util.List;

/**
 * 积分发放产出值对象：{@code MemberGrowth.awardCoursePoints} 的返回值。
 *
 * <p>与签到产出 {@link SignOutcome} 平行——把"发放了多少分、产生了哪些领域事件"打包返回，
 * 由应用层据此落库流水并发布事件。课程完成奖励与签到奖励是两类不同概念，故各自独立建模。
 */
public record AwardOutcome(Points granted, List<DomainEvent> events) {
}
