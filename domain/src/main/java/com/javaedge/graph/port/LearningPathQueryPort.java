package com.javaedge.graph.port;

import com.javaedge.graph.model.LearningPathStep;

import java.util.List;

/**
 * 学习路径图查询端口（领域层定义，基础设施层实现）。
 *
 * <p>对应 MySQL 中 roadmap + roadmap_detail + source_course 三表的关系遍历：
 * 按学习路线取出有序步骤及其关联课程。实现在 infra 模块（Neo4j），
 * 本模块只依赖纯 Java，保持领域层零框架污染。
 */
public interface LearningPathQueryPort {

    /**
     * 查询指定学习路线的有序步骤（含每步关联的课程）。
     *
     * @param roadMapId 学习路线 ID（与 MySQL roadmap.id 一致）
     * @return 按 seq 升序的步骤列表；路线不存在时返回空列表
     */
    List<LearningPathStep> findLearningPath(Integer roadMapId);
}
