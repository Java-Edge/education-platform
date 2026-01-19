package com.javaedge.common.drools.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程推荐规则事实对象
 * 
 * @author JavaEdge
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseRecommendFact {

    /**
     * 课程ID
     */
    private Integer courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程类型 (0:视频 1:专栏)
     */
    private Integer courseType;

    /**
     * 页面访问量
     */
    private Integer pageView;

    /**
     * 课程类别
     */
    private Integer category;

    /**
     * 推荐分数
     */
    private Integer recommendScore;

    /**
     * 推荐理由
     */
    private String recommendReason;

    /**
     * 是否推荐
     */
    private Boolean isRecommended;

    /**
     * 推荐优先级 (1-5，数字越大优先级越高)
     */
    private Integer priority;
}
