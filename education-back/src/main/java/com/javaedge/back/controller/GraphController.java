package com.javaedge.back.controller;

import com.javaedge.application.graph.LearningPathApplicationService;
import com.javaedge.common.resp.ResultBody;
import com.javaedge.graph.model.LearningPathStep;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 图能力 REST 入口（展示层）。
 *
 * <p>仅做协议转换，业务委托给应用层 {@link LearningPathApplicationService}。
 * 暴露：GET /graph/learning-path/{roadMapId} —— 返回某学习路线的有序步骤及其关联课程。
 *
 * <p>鉴权说明：本端点未登记进 need-login 路径表，走默认放行；
 * 生产环境应参照 {@code PermissionConfig} 接入统一鉴权。
 */
@RestController
@RequestMapping("/graph")
@RequiredArgsConstructor
public class GraphController {

    private final LearningPathApplicationService learningPathApplicationService;

    @GetMapping("/learning-path/{roadMapId}")
    public ResultBody learningPath(@PathVariable Integer roadMapId) {
        List<LearningPathStep> steps = learningPathApplicationService.findLearningPath(roadMapId);
        return ResultBody.success(steps);
    }
}
