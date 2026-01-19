package com.javaedge.back.service;

import com.javaedge.common.drools.model.CourseRecommendFact;
import com.javaedge.common.drools.model.UserLevelFact;

import java.util.List;

/**
 * Drools规则引擎示例服务
 * 
 * @author JavaEdge
 */
public interface DroolsExampleService {

    /**
     * 计算课程推荐分数
     * 
     * @param courseId 课程ID
     * @return 推荐结果
     */
    CourseRecommendFact calculateCourseRecommend(Integer courseId);

    /**
     * 批量计算课程推荐
     * 
     * @param courseIds 课程ID列表
     * @return 推荐结果列表
     */
    List<CourseRecommendFact> batchCalculateCourseRecommend(List<Integer> courseIds);

    /**
     * 计算用户等级
     * 
     * @param userId 用户ID
     * @return 用户等级信息
     */
    UserLevelFact calculateUserLevel(Integer userId);

    /**
     * 获取推荐课程列表（按推荐分数排序）
     * 
     * @param limit 返回数量
     * @return 推荐课程列表
     */
    List<CourseRecommendFact> getRecommendedCourses(int limit);
}
