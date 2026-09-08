package com.javaedge.growth.event;

import com.javaedge.growth.valueobject.MemberId;
import com.javaedge.growth.valueobject.MemberLevel;

import java.time.Instant;

/**
 * 会员升级事件。
 */
public record MemberLevelUpgradedEvent(MemberId memberId, MemberLevel from, MemberLevel to, Instant occurredOn)
        implements DomainEvent {

    public MemberLevelUpgradedEvent {
        occurredOn = occurredOn == null ? Instant.now() : occurredOn;
    }

    public static MemberLevelUpgradedEvent of(MemberId memberId, MemberLevel from, MemberLevel to) {
        return new MemberLevelUpgradedEvent(memberId, from, to, Instant.now());
    }
}
