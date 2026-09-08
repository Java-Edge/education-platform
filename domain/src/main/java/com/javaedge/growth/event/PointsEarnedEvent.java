package com.javaedge.growth.event;

import com.javaedge.growth.valueobject.MemberId;
import com.javaedge.growth.valueobject.Points;

import java.time.Instant;

/**
 * 积分获取事件。source 标识来源，如 "SIGN"（签到）、"COURSE"（课程完成）。
 */
public record PointsEarnedEvent(MemberId memberId, Points amount, String source, Instant occurredOn)
        implements DomainEvent {

    public PointsEarnedEvent {
        occurredOn = occurredOn == null ? Instant.now() : occurredOn;
    }

    public static PointsEarnedEvent of(MemberId memberId, Points amount, String source) {
        return new PointsEarnedEvent(memberId, amount, source, Instant.now());
    }
}
