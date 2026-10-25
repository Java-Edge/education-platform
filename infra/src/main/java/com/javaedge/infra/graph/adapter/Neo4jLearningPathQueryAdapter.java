package com.javaedge.infra.graph.adapter;

import com.javaedge.graph.model.LearningPathStep;
import com.javaedge.graph.port.LearningPathQueryPort;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.TypeSystem;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

/**
 * 学习路径图查询适配器（基础设施层实现领域端口）。
 *
 * <p>唯一职责：把领域查询（按路线取有序步骤）翻译为 Cypher，并经 SDN 的
 * 命令式 {@link Neo4jClient} 取回原始 {@link Record}，逐行映射为领域模型
 * {@link LearningPathStep}。图存储细节（Label / 关系 / Cypher）被封锁在本类，
 * 领域层保持零框架污染。
 *
 * <p>Neo4j 不可用时不抛异常给上层——由调用方（控制器）决定降级为 MySQL 查询，
 * 保证图库是"增强"而非"依赖"。
 */
@Slf4j
@Repository
public class Neo4jLearningPathQueryAdapter implements LearningPathQueryPort {

    private final Neo4jClient neo4jClient;

    public Neo4jLearningPathQueryAdapter(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    /**
     * 路线展开：RoadMap -[:NEXT_STEP]-> RoadMapDetail -[:REFERENCES]-> Course。
     * 顺序由关系上的 seq 属性保证，对应 MySQL 的 map_order。
     */
    private static final String FIND_PATH = """
            MATCH (r:RoadMap {mysql_id: $roadMapId})-[:NEXT_STEP]->(d:RoadMapDetail)
            OPTIONAL MATCH (d)-[:REFERENCES]->(c:Course)
            RETURN d.map_order AS seq,
                   d.mysql_id AS detailId,
                   d.title    AS detailTitle,
                   c.mysql_id AS courseId,
                   c.name     AS courseName,
                   c.type     AS courseType,
                   c.source_url AS courseUrl
            ORDER BY seq
            """;

    private static final BiFunction<TypeSystem, Record, LearningPathStep> MAPPER =
            (ts, rec) -> new LearningPathStep(
                    integerOrNull(rec.get("seq")),
                    integerOrNull(rec.get("detailId")),
                    stringOrNull(rec.get("detailTitle")),
                    integerOrNull(rec.get("courseId")),
                    stringOrNull(rec.get("courseName")),
                    integerOrNull(rec.get("courseType")),
                    stringOrNull(rec.get("courseUrl"))
            );

    @Override
    public List<LearningPathStep> findLearningPath(Integer roadMapId) {
        Collection<LearningPathStep> steps = neo4jClient.query(FIND_PATH)
                .bind(roadMapId).to("roadMapId")
                .fetchAs(LearningPathStep.class)
                .mappedBy(MAPPER)
                .all();
        return new ArrayList<>(steps);
    }

    private static Integer integerOrNull(Value v) {
        return v.isNull() ? null : v.asInt();
    }

    private static String stringOrNull(Value v) {
        return v.isNull() ? null : v.asString();
    }
}
