package com.javaedge.growth.valueobject;

import java.util.Objects;

/**
 * 会员标识值对象（Value Object）。
 *
 * <p>值对象三特征：
 * <ol>
 *   <li>不可变：构造后属性不可改（record 天然保证）；</li>
 *   <li>以属性值判等：两个 MemberId 只要 value 相同即相等；</li>
 *   <li>无独立生命周期：依附于所属聚合根，不能脱离聚合单独存在。</li>
 * </ol>
 *
 * 用值对象承载"会员标识"而非裸 Integer，是为了把"ID 必须为正"这类约束收敛到一处，
 * 避免散落在各处的 {@code userId > 0} 校验。
 */
public record MemberId(Integer value) {

    public MemberId {
        Objects.requireNonNull(value, "会员ID不能为空");
        if (value <= 0) {
            throw new IllegalArgumentException("会员ID必须为正整数");
        }
    }

    public static MemberId of(Integer value) {
        return new MemberId(value);
    }
}
