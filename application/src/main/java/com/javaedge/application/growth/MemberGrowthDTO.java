package com.javaedge.application.growth;

/**
 * 会员成长快照 DTO（出参）：用于查询会员的积分、等级与本月签到情况。
 */
public record MemberGrowthDTO(
        Integer memberId,
        int totalPoints,
        int level,
        int thisMonthSignedDays) {
}
