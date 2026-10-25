package com.javaedge.graph.model;

/**
 * 学习路线中的一个步骤（图查询投影 / 读模型）。
 *
 * <p>该类型是领域层对象，由基础设施层（Neo4j 适配器）填充，
 * 不依赖任何 Spring Data Neo4j 注解——图存储细节被封锁在 infra 模块内。
 */
public record LearningPathStep(
        Integer seq,
        Integer detailId,
        String detailTitle,
        Integer courseId,
        String courseName,
        Integer courseType,
        String courseUrl) {
}
