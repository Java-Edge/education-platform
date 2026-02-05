package com.javaedge.common.ai.service;

import com.javaedge.common.ai.model.CourseAiRecommendRequest;

import java.util.List;

/**
 * 课程AI推荐服务接口
 * 基于AI的智能课程推荐
 *
 * @author JavaEdge
 */
public interface CourseAiService {

    /**
     * 基于用户画像生成课程推荐
     *
     * @param request 推荐请求
     * @return 推荐课程ID列表
     */
    List<Integer> recommendCourses(CourseAiRecommendRequest request);

    /**
     * 生成课程描述
     *
     * @param courseName 课程名称
     * @param courseType 课程类型
     * @param keywords 关键词
     * @return AI生成的课程描述
     */
    String generateCourseDescription(String courseName, String courseType, String keywords);

    /**
     * 生成课程大纲
     *
     * @param courseName 课程名称
     * @param targetAudience 目标受众
     * @param duration 课程时长
     * @return AI生成的课程大纲
     */
    String generateCourseOutline(String courseName, String targetAudience, String duration);

    /**
     * 分析用户学习路径
     *
     * @param userId 用户ID
     * @param completedCourses 已完成课程
     * @param learningGoal 学习目标
     * @return 学习路径建议
     */
    String analyzeLearningPath(Integer userId, List<String> completedCourses, String learningGoal);
}
