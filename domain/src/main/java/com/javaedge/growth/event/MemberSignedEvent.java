package com.javaedge.growth.event;

import com.javaedge.growth.valueobject.MemberId;
import com.javaedge.growth.valueobject.Points;
import com.javaedge.growth.valueobject.SignDate;

import java.time.Instant;

/**
 * 会员签到完成事件。
 */
public record MemberSignedEvent(MemberId memberId, SignDate date, Points basePoints, Instant occurredOn)
        implements DomainEvent {

    public MemberSignedEvent {
        occurredOn = occurredOn == null ? Instant.now() : occurredOn;
    }

    public static MemberSignedEvent of(MemberId memberId, SignDate date, Points basePoints) {
        return new MemberSignedEvent(memberId, date, basePoints, Instant.now());
    }
}
