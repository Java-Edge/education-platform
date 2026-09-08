package com.javaedge.growth.entity;

import com.javaedge.growth.valueobject.MemberId;
import com.javaedge.growth.valueobject.SignDate;

import java.util.Objects;

/**
 * 签到记录：聚合内的<b>实体（Entity）</b>。
 *
 * <p>与值对象不同，实体有独立身份——它的标识由"会员 + 签到日期"构成（业务键），
 * 即便两次签到积分相同，只要不是同一天，就是两条不同的签到记录。
 * 因此 {@code equals/hashCode} 基于业务键而非全部属性。
 */
public class SignRecord {

    private final MemberId memberId;
    private final SignDate signDate;

    public SignRecord(MemberId memberId, SignDate signDate) {
        this.memberId = Objects.requireNonNull(memberId);
        this.signDate = Objects.requireNonNull(signDate);
    }

    public MemberId memberId() {
        return memberId;
    }

    public SignDate signDate() {
        return signDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignRecord that)) {
            return false;
        }
        return memberId.equals(that.memberId) && signDate.equals(that.signDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, signDate);
    }
}
