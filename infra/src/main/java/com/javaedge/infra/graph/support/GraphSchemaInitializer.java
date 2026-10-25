package com.javaedge.infra.graph.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 图 schema 初始化（基础设施层）。
 *
 * <p>启动时幂等创建唯一约束：{@code mysql_id} 业务键唯一约束既是 MERGE 的锚点，
 * 也是 CDC / 批量投影不产生重复节点的唯一可靠保证。
 *
 * <p>Neo4j 不可用时不阻断主站启动——图库是派生只读存储，降级为 MySQL 查询即可。
 *
 * <p>SDN 不会把 {@code org.neo4j.driver.QueryRunner} 注册为 Spring Bean；
 * 通过 {@link Neo4jClient#getQueryRunner()} 取得执行器。
 */
@Slf4j
@Component
@Order(1)
public class GraphSchemaInitializer implements ApplicationRunner {

    private final Neo4jClient neo4jClient;

    @Autowired
    public GraphSchemaInitializer(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    private static final List<String> CONSTRAINTS = List.of(
            "CREATE CONSTRAINT course_mysql_id IF NOT EXISTS FOR (c:Course) REQUIRE c.mysql_id IS UNIQUE",
            "CREATE CONSTRAINT roadmap_mysql_id IF NOT EXISTS FOR (r:RoadMap) REQUIRE r.mysql_id IS UNIQUE",
            "CREATE CONSTRAINT roadmap_detail_mysql_id IF NOT EXISTS FOR (d:RoadMapDetail) REQUIRE d.mysql_id IS UNIQUE"
    );

    @Override
    public void run(ApplicationArguments args) {
        try {
            for (String cypher : CONSTRAINTS) {
                neo4jClient.getQueryRunner().run(cypher);
            }
            log.info("Neo4j 图 schema 初始化完成：{} 条唯一约束生效", CONSTRAINTS.size());
        } catch (Exception e) {
            log.warn("Neo4j 图 schema 初始化失败（图库不可用不影响主站）：{}", e.getMessage());
        }
    }
}
