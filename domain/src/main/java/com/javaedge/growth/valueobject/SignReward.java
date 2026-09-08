package com.javaedge.growth.valueobject;

/**
 * 单次签到奖励值对象：基础分 + 连续签到奖励分 + 文案描述。
 *
 * <p>由 {@link com.javaedge.growth.service.SignRewardPolicy} 领域服务产出。
 * 聚合根只消费结果，不关心奖励怎么算——规则可插拔（配置 / Drools 规则引擎）。
 */
public record SignReward(int base, int bonus, String description) {

    public SignReward {
        if (base < 0 || bonus < 0) {
            throw new IllegalArgumentException("奖励分不能为负");
        }
        description = description == null ? "" : description;
    }

    public Points total() {
        return Points.of(base + bonus);
    }

    public static SignReward of(int base, int bonus, String description) {
        return new SignReward(base, bonus, description);
    }
}
