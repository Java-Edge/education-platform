package com.javaedge.common.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程AI推荐请求
 *
 * @author JavaEdge
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseAiRecommendRequest {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户学习偏好描述
     */
    private String preference;

    /**
     * 用户当前技能水平
     */
    private String skillLevel;

    /**
     * 学习目标
     */
    private String learningGoal;

    /**
     * 推荐课程数量
     */
    @Builder.Default
    private Integer limit = 5;
}
