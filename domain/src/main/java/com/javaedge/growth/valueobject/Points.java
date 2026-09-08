package com.javaedge.growth.valueobject;

/**
 * 积分值对象。
 *
 * <p>关键设计：所有运算都返回<b>新的</b> Points 实例，绝不修改自身。
 * 聚合根累加积分时写成 {@code totalPoints = totalPoints.add(reward)}，
 * 这种"不可变 + 返回新值"的风格让状态变更路径清晰、可追踪，也天然线程友好。
 */
public record Points(int amount) {

    public Points {
        if (amount < 0) {
            throw new IllegalArgumentException("积分不能为负");
        }
    }

    public Points add(Points other) {
        return new Points(this.amount + other.amount());
    }

    public static Points of(int amount) {
        return new Points(amount);
    }
}
