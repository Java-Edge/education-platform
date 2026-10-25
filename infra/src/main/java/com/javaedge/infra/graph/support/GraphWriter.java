package com.javaedge.infra.graph.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 图写入组件（基础设施层）：MySQL 关系数据 -> Neo4j 节点/关系的投影。
 *
 * <p>写入全部走 {@code MERGE}（业务键 {@code mysql_id} 唯一约束兜底幂等），
 * 可重复执行、可从任意断点重跑——这是后期替换为 Debezium CDC 管道的前提。
 *
 * <p>注意（Neo4j 官方性能纪律）：批量写入按"一个接一个的事务"串行提交，
 * 不用并行加载——写关系需要锁定两端节点，并行只会换来锁等待。
 *
 * <p>SDN 不会把 {@code org.neo4j.driver.QueryRunner} 注册为 Spring Bean；
 * 通过 {@link Neo4jClient#getQueryRunner()} 取得（底层即 Driver，其实现了 QueryRunner）。
 */
@Slf4j
@Component
public class GraphWriter {

    private final Neo4jClient neo4jClient;

    @Autowired
    public GraphWriter(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    private static final String UPSERT_COURSES = """
            UNWIND $batch AS c
            MERGE (co:Course {mysql_id: c.mysqlId})
            SET co.name = c.name, co.type = c.type, co.source_url = c.sourceUrl
            WITH co, c
            WHERE c.parentId IS NOT NULL
            MERGE (parent:Course {mysql_id: c.parentId})
            MERGE (co)-[:SUB_OF]->(parent)
            """;

    private static final String UPSERT_ROADMAPS = """
            UNWIND $batch AS r
            MERGE (rm:RoadMap {mysql_id: r.mysqlId})
            SET rm.title = r.title, rm.step = r.step
            """;

    private static final String UPSERT_DETAILS = """
            UNWIND $batch AS d
            MERGE (dt:RoadMapDetail {mysql_id: d.mysqlId})
            SET dt.title = d.title, dt.map_order = d.mapOrder, dt.type = d.type
            WITH dt, d
            MATCH (rm:RoadMap {mysql_id: d.roadMapId})
            MERGE (rm)-[:NEXT_STEP {seq: d.mapOrder}]->(dt)
            WITH dt, d
            WHERE d.courseId IS NOT NULL
            MATCH (c:Course {mysql_id: d.courseId})
            MERGE (dt)-[:REFERENCES]->(c)
            """;

    public void upsertCourses(Collection<CourseUpsert> items) {
        if (items.isEmpty()) {
            return;
        }
        neo4jClient.getQueryRunner().run(UPSERT_COURSES, Map.of("batch", items.stream()
                .map(i -> nullSafeMap("mysqlId", i.mysqlId(), "name", i.name(),
                        "type", i.type(), "sourceUrl", i.sourceUrl(), "parentId", i.parentId()))
                .toList()));
        log.debug("Course 投影 {} 条", items.size());
    }

    public void upsertRoadMaps(Collection<RoadMapUpsert> items) {
        if (items.isEmpty()) {
            return;
        }
        neo4jClient.getQueryRunner().run(UPSERT_ROADMAPS, Map.of("batch", items.stream()
                .map(i -> nullSafeMap("mysqlId", i.mysqlId(), "title", i.title(), "step", i.step()))
                .toList()));
        log.debug("RoadMap 投影 {} 条", items.size());
    }

    public void upsertDetails(Collection<DetailUpsert> items) {
        if (items.isEmpty()) {
            return;
        }
        neo4jClient.getQueryRunner().run(UPSERT_DETAILS, Map.of("batch", items.stream()
                .map(i -> nullSafeMap("mysqlId", i.mysqlId(), "title", i.title(),
                        "mapOrder", i.mapOrder(), "type", i.type(),
                        "roadMapId", i.roadMapId(), "courseId", i.courseId()))
                .toList()));
        log.debug("RoadMapDetail 投影 {} 条", items.size());
    }

    /**
     * 构建允许 null 值的参数 Map（{@code Map.of} 不接受 null）。
     */
    private static Map<String, Object> nullSafeMap(Object... kv) {
        Map<String, Object> m = new LinkedHashMap<>();
        for (int i = 0; i < kv.length; i += 2) {
            m.put((String) kv[i], kv[i + 1]);
        }
        return m;
    }

    public record CourseUpsert(Integer mysqlId, String name, Integer type, String sourceUrl, Integer parentId) {
    }

    public record RoadMapUpsert(Integer mysqlId, String title, Integer step) {
    }

    public record DetailUpsert(Integer mysqlId, String title, Integer mapOrder, Integer type,
                               Integer roadMapId, Integer courseId) {
    }
}
