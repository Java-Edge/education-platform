package com.javaedge.application.graph;

import com.javaedge.graph.model.LearningPathStep;
import com.javaedge.graph.port.LearningPathQueryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学习路径应用服务（应用层）。
 *
 * <p>薄封装：仅做编排与协议转换，不含业务规则。读模型来自领域端口
 * {@link LearningPathQueryPort}——当前实现是 Neo4j 图库，未来可无痛替换为
 * MySQL 直查或 ES，应用层与控制器无需改动（端口/适配器边界的价值）。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LearningPathApplicationService {

    private final LearningPathQueryPort learningPathQueryPort;

    /**
     * 查询指定学习路线的有序步骤。
     */
    public List<LearningPathStep> findLearningPath(Integer roadMapId) {
        return learningPathQueryPort.findLearningPath(roadMapId);
    }
}
