package com.javaedge.growth.valueobject;

/**
 * 会员等级值对象。
 *
 * <p>等级不是独立存储的"状态"，而是<b>累计积分的纯函数推导结果</b>：
 * 每满 500 积分升一级（示例阈值）。把等级定义为值对象而非实体，
 * 是为了消除"积分变了但等级忘了同步"这类一致性 bug——等级永远由积分算出来。
 */
public record MemberLevel(int level) {

    public MemberLevel {
        if (level < 1) {
            throw new IllegalArgumentException("等级最低为 1");
        }
    }

    /** 由累计积分推导等级。 */
    public static MemberLevel fromPoints(Points points) {
        return new MemberLevel(Math.max(1, points.amount() / 500 + 1));
    }

    public static MemberLevel of(int level) {
        return new MemberLevel(level);
    }

    /** 在积分变化后，重算并返回（可能不变的）最新等级。 */
    public MemberLevel upgradeIfNeeded(Points points) {
        return fromPoints(points);
    }

    public boolean isHigherThan(MemberLevel other) {
        return this.level > other.level;
    }

    public static MemberLevel initial() {
        return new MemberLevel(1);
    }
}
