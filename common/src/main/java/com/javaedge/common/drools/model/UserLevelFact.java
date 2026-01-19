package com.javaedge.common.drools.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户等级规则事实对象
 * 
 * @author JavaEdge
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLevelFact {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户总积分
     */
    private Integer totalIntegral;

    /**
     * 连续签到天数
     */
    private Integer continuousSignDays;

    /**
     * 发表文章数
     */
    private Integer articleCount;

    /**
     * 课程学习数
     */
    private Integer courseLearnCount;

    /**
     * 用户等级 (1-10)
     */
    private Integer userLevel;

    /**
     * 等级名称
     */
    private String levelName;

    /**
     * 等级权益描述
     */
    private String levelBenefits;

    /**
     * 下一等级所需积分
     */
    private Integer nextLevelIntegral;
}
