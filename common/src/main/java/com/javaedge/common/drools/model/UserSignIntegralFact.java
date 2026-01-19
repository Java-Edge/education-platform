package com.javaedge.common.drools.model;

import lombok.*;

import java.time.LocalDate;

/**
 * 用户签到积分规则事实对象
 * 
 * @author JavaEdge
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignIntegralFact {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 连续签到天数
     */
    private Integer continuousSignDays;

    /**
     * 当前月份
     */
    private Integer currentMonth;

    /**
     * 当前月份总天数
     */
    private Integer totalDaysInMonth;

    /**
     * 是否是整月签到
     */
    private Boolean isFullMonthSign;

    /**
     * 基础签到积分
     */
    private Integer baseIntegral;

    /**
     * 额外奖励积分
     */
    private Integer bonusIntegral;

    /**
     * 总积分（基础+奖励）
     */
    private Integer totalIntegral;

    /**
     * 奖励类型描述
     */
    private String bonusDescription;

    /**
     * 构建签到事实对象
     */
    public static UserSignIntegralFact buildSignFact(Integer userId, Integer continuousSignDays) {
        LocalDate now = LocalDate.now();
        return UserSignIntegralFact.builder()
                .userId(userId)
                .continuousSignDays(continuousSignDays)
                .currentMonth(now.getMonthValue())
                .totalDaysInMonth(now.lengthOfMonth())
                .isFullMonthSign(continuousSignDays >= now.lengthOfMonth())
                .baseIntegral(0)
                .bonusIntegral(0)
                .totalIntegral(0)
                .bonusDescription("")
                .build();
    }

    /**
     * 计算总积分
     */
    public void calculateTotal() {
        this.totalIntegral = this.baseIntegral + this.bonusIntegral;
    }
}
