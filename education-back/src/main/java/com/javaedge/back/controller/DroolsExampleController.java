package com.javaedge.back.controller;

import com.javaedge.back.service.DroolsExampleService;
import com.javaedge.common.drools.model.CourseRecommendFact;
import com.javaedge.common.drools.model.UserLevelFact;
import com.javaedge.common.resp.ResultBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Drools规则引擎示例控制器
 * 提供API接口展示规则引擎的使用
 * 
 * @author JavaEdge
 */
@RestController
@RequestMapping("/drools")
@RequiredArgsConstructor
public class DroolsExampleController {

    private final DroolsExampleService droolsExampleService;

    /**
     * 计算课程推荐分数
     * GET /drools/course/recommend/{courseId}
     */
    @GetMapping("/course/recommend/{courseId}")
    public ResultBody getCourseRecommend(@PathVariable Integer courseId) {
        CourseRecommendFact fact = droolsExampleService.calculateCourseRecommend(courseId);
        return ResultBody.success(fact);
    }

    /**
     * 批量计算课程推荐
     * POST /drools/course/recommend/batch
     */
    @PostMapping("/course/recommend/batch")
    public ResultBody batchGetCourseRecommend(@RequestBody List<Integer> courseIds) {
        List<CourseRecommendFact> facts = droolsExampleService.batchCalculateCourseRecommend(courseIds);
        return ResultBody.success(facts);
    }

    /**
     * 获取推荐课程列表
     * GET /drools/course/recommended?limit=10
     */
    @GetMapping("/course/recommended")
    public ResultBody getRecommendedCourses(@RequestParam(defaultValue = "10") int limit) {
        List<CourseRecommendFact> facts = droolsExampleService.getRecommendedCourses(limit);
        return ResultBody.success(facts);
    }

    /**
     * 计算用户等级
     * GET /drools/user/level/{userId}
     */
    @GetMapping("/user/level/{userId}")
    public ResultBody getUserLevel(@PathVariable Integer userId) {
        UserLevelFact fact = droolsExampleService.calculateUserLevel(userId);
        return ResultBody.success(fact);
    }
}
