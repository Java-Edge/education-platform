package com.javaedge.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.javaedge.back.entity.CoursePO;
import com.javaedge.back.entity.UserIntegral;
import com.javaedge.back.mapper.CourseMapper;
import com.javaedge.back.mapper.UserIntegralMapper;
import com.javaedge.back.service.DroolsExampleService;
import com.javaedge.common.drools.DroolsRuleService;
import com.javaedge.common.drools.model.CourseRecommendFact;
import com.javaedge.common.drools.model.UserLevelFact;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Drools规则引擎示例服务实现
 * 展示如何在实际业务中使用规则引擎
 * 
 * @author JavaEdge
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DroolsExampleServiceImpl implements DroolsExampleService {

    private final DroolsRuleService droolsRuleService;
    private final CourseMapper courseMapper;
    private final UserIntegralMapper userIntegralMapper;

    @Override
    public CourseRecommendFact calculateCourseRecommend(Integer courseId) {
        // 查询课程信息
        CoursePO course = courseMapper.selectById(courseId);
        if (course == null) {
            log.warn("课程不存在: {}", courseId);
            return null;
        }

        // 构建事实对象
        CourseRecommendFact fact = CourseRecommendFact.builder()
                .courseId(course.getId())
                .courseName(course.getName())
                .courseType(course.getType())
                .pageView(course.getPageView())
                .category(course.getParentId())
                .recommendScore(0)
                .recommendReason("")
                .build();

        // 执行规则
        droolsRuleService.executeRulesWithGlobal(fact, "logger", LoggerFactory.getLogger("drools.rules"));
        
        log.info("课程推荐结果 - 课程: {}, 推荐分数: {}, 优先级: {}, 理由: {}", 
            fact.getCourseName(), 
            fact.getRecommendScore(), 
            fact.getPriority(),
            fact.getRecommendReason());

        return fact;
    }

    @Override
    public List<CourseRecommendFact> batchCalculateCourseRecommend(List<Integer> courseIds) {
        return courseIds.stream()
                .map(this::calculateCourseRecommend)
                .filter(fact -> fact != null)
                .collect(Collectors.toList());
    }

    @Override
    public UserLevelFact calculateUserLevel(Integer userId) {
        // 查询用户积分
        LambdaQueryWrapper<UserIntegral> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserIntegral::getUserId, userId);
        UserIntegral userIntegral = userIntegralMapper.selectOne(wrapper);

        int totalIntegral = userIntegral != null ? userIntegral.getIntegral() : 0;

        // 构建事实对象
        UserLevelFact fact = UserLevelFact.builder()
                .userId(userId)
                .totalIntegral(totalIntegral)
                .continuousSignDays(0)  // 这里可以从签到记录获取
                .articleCount(0)  // 这里可以从文章表获取
                .courseLearnCount(0)  // 这里可以从学习记录获取
                .build();

        // 执行规则
        droolsRuleService.executeRulesWithGlobal(fact, "logger", LoggerFactory.getLogger("drools.rules"));

        log.info("用户等级计算结果 - 用户ID: {}, 积分: {}, 等级: {}, 等级名称: {}, 权益: {}", 
            userId, 
            totalIntegral, 
            fact.getUserLevel(),
            fact.getLevelName(),
            fact.getLevelBenefits());

        return fact;
    }

    @Override
    public List<CourseRecommendFact> getRecommendedCourses(int limit) {
        // 查询所有课程
        List<CoursePO> courses = courseMapper.selectList(null);

        // 计算推荐分数
        List<CourseRecommendFact> recommendFacts = courses.stream()
                .map(course -> {
                    CourseRecommendFact fact = CourseRecommendFact.builder()
                            .courseId(course.getId())
                            .courseName(course.getName())
                            .courseType(course.getType())
                            .pageView(course.getPageView())
                            .category(course.getParentId())
                            .recommendScore(0)
                            .recommendReason("")
                            .build();

                    droolsRuleService.executeRulesWithGlobal(fact, "logger", LoggerFactory.getLogger("drools.rules"));
                    return fact;
                })
                .filter(fact -> fact.getIsRecommended() != null && fact.getIsRecommended())
                .sorted(Comparator.comparing(CourseRecommendFact::getRecommendScore).reversed())
                .limit(limit)
                .collect(Collectors.toList());

        log.info("获取推荐课程列表，数量: {}", recommendFacts.size());
        return recommendFacts;
    }
}
