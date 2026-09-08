package com.javaedge.application.growth;

import java.util.List;

/**
 * 签到结果 DTO（出参）：应用层把领域产出（奖励、连续天数、事件）投影成对外视图。
 *
 * <p>出参与领域对象解耦——客户端看到的是扁平的、友好的字段，不感知聚合根/值对象等内部结构。
 */
public record SignResultDTO(
        Integer memberId,
        int totalPoints,
        int level,
        int continuousDays,
        int baseReward,
        int bonusReward,
        String rewardDescription,
        List<String> eventTypes) {
}
