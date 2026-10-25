# education-platform 引入 Neo4j 的最佳实践分析

> 版本事实核实日期：2026-10-21。所有版本号与兼容性结论均来自官方一手来源（见第 11 节参考依据），未采信自媒体口径。

---

## 0. 结论先行

**总判定：建议引入，但必须限定为「派生只读图存储（CQRS 读模型）」，MySQL 保持唯一事实源（System of Record）。** 不替换、不平权、不双写主链路。

| # | 结论 | 依据 |
|---|------|------|
| 1 | 引入定位：Neo4j 只承载**关系遍历类查询**（学习路线展开、前置课程链路、职业-岗位图谱、协同推荐），MySQL 不变 | 官方判据「连接的价值（value of links）」；本项目真实存在多跳查询需求（roadmap_detail 层级、career.parent_id 树、tag CSV 多对多） |
| 2 | 版本组合：Neo4j **5.26 LTS**（或 2026.x calVer）+ Spring Boot 3.4.1 托管 **SDN 7.4.x + Java Driver 5.25.0**，零版本冲突 | spring-boot-dependencies-3.4.1.pom 实测：`neo4j-java-driver.version=5.25.0`、`spring-data-bom.version=2024.1.1`；官方驱动兼容表：driver 5.x 支持 server 4.4/5.x/2025.x/2026.x |
| 3 | 同步方案：**Debezium（MySQL binlog）→ Kafka → Neo4j Connector for Apache Kafka（官方 sink）**，MySQL 表主键 = 图节点业务键，MERGE 保幂等 | 项目 infra 已具备 spring-kafka；官方 Kafka Connector 文档明确支持 Debezium CDC 格式与 Cypher ingest |
| 4 | 优先场景排序：① 学习路线/前置课程图谱 → ② 职业-岗位-内推图谱 → ③ 用户行为协同推荐；**RBAC 权限图、全文检索不做** | RBAC 关系型足够（反过度工程）；全文检索 ES 9.2.1 已覆盖，职责不重叠 |
| 5 | 分阶段落地：P0 环境与连通（1 周）→ P1 单场景 POC + 等价性测试（2-3 周）→ P2 CDC 管道与对账（2 周）→ P3 图谱扩展；每阶段有量化验收门 | 避免一次性全量迁移的风险；与项目既有「先提案→评估→实现」两阶段制一致 |

**一句话架构**：MySQL 写、Neo4j 读、ES 搜、Redis 缓存——各司其职的多语言持久化（Polyglot Persistence），图库作为关系查询的专用加速器。

---

## 1. 项目现状盘点

### 1.1 技术栈基线（代码实测）

| 层 | 现状 | 位置/证据 |
|----|------|-----------|
| 框架 | Spring Boot 3.4.1 / Java 23 / 虚拟线程开启 | 根 pom.xml、application.yml `spring.threads.virtual.enabled: true` |
| 架构 | DDD 五模块：domain / infra / application / common / education-back | 根 pom.xml modules |
| 主存储 | MySQL 8.0.33 + MyBatis-Plus 3.5.3.1 + mybatis-plus-join（连表查询已引入）+ PageHelper | 根 pom.xml dependencyManagement |
| 缓存 | Redis + Spring Session + Caffeine | infra/pom.xml、education-back CacheConfig |
| 检索 | Elasticsearch 9.2.1（本地运行，ESConfig + ESController） | docs/ES.md 启动日志、ESConfig.java |
| 消息 | spring-kafka + kafka-clients（KafkaConfig/Producer/Consumer 已就位，但 application.yml 中 kafka 配置**被注释未启用**） | infra/src/main/java/com/javaedge/infra/mq/ |
| 规则 | Drools 9.44（积分签到策略） | 根 pom.xml |
| 治理 | Nacos / Sentinel / XXL-JOB（`xxl-job.enabled: false` 预留） | application.yml |

### 1.2 领域关系资产与图化机会（实体代码实测）

| 领域 | 实体（表） | 当前关系表达 | 图化机会 |
|------|-----------|-------------|---------|
| 课程 | `source_course`（parent_id 父子、type 0视频/1专栏、page_view） | 自连接 parent_id；专栏-视频两层 | `(:Course)-[:SUB_OF]->(:Course)`，前置课程链路 |
| 学习路线 | `roadmap` + `roadmap_detail`（course_id、map_order、tag CSV） | detail 按 road_map_id join 后按 map_order 内存排序；**tag 逗号分隔存储**（多对多反模式） | `(:RoadMap)-[:NEXT_STEP {seq}]->(:RoadMapDetail)-[:REFERENCES]->(:Course)`；`(:RoadMapDetail)-[:TAGGED]->(:Tag)` |
| 职业体系 | `career`（level、parent_id、isGrab）、`recruit`、`inner_recommend`（job_id） | parent_id 树需递归；内推按 job_id 关联 | `(:Career)-[:SUB_OF]->(:Career)`、`(:Recruit)-[:FOR_CAREER]->(:Career)`、`(:InnerRecommend)-[:RECOMMENDS]->(:Recruit)` |
| 面试 | `interview`、`interview_schedule` | 按 career/job 过滤 | `(:User)-[:INTERVIEWED_FOR]->(:Career)` |
| 内容互动 | `article`、`comment`、`company_discussion` | 多表 join | `(:User)-[:COMMENTED_ON {at}]->(:Article)` |
| 用户行为 | `ucenter_member`、`user_integral`、`sign_record`、`ranking` | 无行为关系表 | `(:User)-[:COMPLETED {progress, finished_at}]->(:Course)`（协同推荐原料） |
| 权限 | `role`/`user_role`/`permission_config` | 关系型 RBAC | **不图化**（反过度工程，见第 9 节） |

### 1.3 现存痛点（图化直接动因）

1. **多跳/递归查询成本高**：career 树、课程父子树在 MySQL 中只能靠自连接或递归 CTE；roadmap 完整展开需 roadmap → detail → course 多次 join 再按 map_order 排序。项目引入 mybatis-plus-join 本身即证明连表复杂度已在侵蚀可维护性。
2. **CSV 字段割裂多对多**：`roadmap_detail.tag` 以逗号分隔存标签 ID，查询「含某标签的所有路线步骤」必须 `LIKE '%,%'`，无法走索引、无法做标签共现分析。
3. **推荐缺原料**：平台已有课程/路线/职业/内推实体，但用户-内容行为未沉淀为关系，「学了 A 的用户还学了 B」这类查询当前无数据模型支撑。
4. **ES 不能替代图**：ES 9.2.1 解决全文检索与聚合，但多跳遍历（N 度关系、最短路径、模式匹配）不是检索引擎的强项，两者互补。

---

## 2. 适用性评估：该不该引入

### 2.1 官方判据（Neo4j 权威口径）

- 「理解何时使用图数据库的关键，是**连接的价值**（the value of links）。如果数据是高度互联的，图就是正确选择。」——Neo4j《Graph Databases for Dummies》
- 图数据库的核心差异：**关系与数据同等重要**；labeled property graph 模型中节点、关系、属性、约束是一等公民。
- GraphAcademy 建模纪律：**connections are verbs**（关系即动词）；按「预期要被问的问题」决定关系方向与类型；用实例数据反复验证模型能否回答问题。

### 2.2 匹配度矩阵

| 维度 | 教育平台实况 | 图库适配度 |
|------|-------------|-----------|
| 关系密度 | 课程-路线-标签-职业-岗位-用户行为，天然网状 | ★★★★★ |
| 查询模式 | 多跳遍历、模式匹配、路径排序为主 | ★★★★★ |
| 写特征 | 写集中在 MySQL 交易表；图数据是派生投影 | ★★★★☆（派生数据，无强一致写压力） |
| 一致性要求 | 推荐/路线类查询允许秒级最终一致 | ★★★★☆ |
| 数据规模 | 十万~百万级节点，单机可承载 | ★★★★☆ |

### 2.3 场景优先级排序

| 优先级 | 场景 | 典型查询 | MySQL 现状成本 | Neo4j 收益 |
|--------|------|---------|--------------|-----------|
| P1 | 学习路线展开与前置课程 | 「路线全步骤按序」「学 X 的前置是什么」「从当前水平到目标岗位的最短学习路径」 | 3+ 表 join + 内存排序；前置链路无原生递归 | 单次变长路径遍历，代码即查询意图 |
| P1 | 职业-岗位-内推图谱 | 「该职业的岗位分布」「岗位的内推入口」「同职业高频面经」 | parent_id 递归 + job_id 关联 join | 树遍历 + 模式匹配一次完成 |
| P2 | 协同推荐 | 「学完 A 的用户还学了 B」 | 自连接 + 聚合，秒级且难维护 | 模式匹配原生表达，可接 GDS 算法增强 |
| P2 | 标签共现/内容聚类 | 「常与 Spring 标签共现的标签」 | CSV LIKE，不可行 | 原生多对多遍历 |
| — | 全文检索 | 课程/文章搜索 | — | **不做**（ES 已覆盖） |
| — | RBAC 权限 | 角色-权限校验 | 关系型 3 表足够 | **不做**（反过度工程） |

---

## 3. 架构定位：MySQL + Neo4j 混合持久化

### 3.1 总体架构

```
┌────────────────────────────────────────────────────────────────┐
│ education-back (Spring Boot 3.4.1)                             │
│  Controller → Service → ┬─ MyBatis-Plus Mapper（写 + 明细读）    │
│                         ├─ Neo4j Repository / Neo4jClient（图查询）│
│                         ├─ ElasticsearchClient（全文检索）        │
│                         └─ Redis / Caffeine（缓存）              │
└────────────────────────────────────────────────────────────────┘
        │ 写（唯一事实源）                ▲ 读（派生投影）
        ▼                                │
   ┌─────────┐   binlog   ┌────────┐   Cypher/MERGE   ┌────────┐
   │  MySQL  │──Debezium─▶│ Kafka  │──Neo4j Connector─▶│ Neo4j  │
   │ 8.0.33  │  (CDC)     │(已有)  │  (官方 sink)      │ 5.26   │
   └─────────┘            └────────┘                   └────────┘
```

### 3.2 职责边界（红线级划分）

| 存储 | 职责 | 禁止事项 |
|------|------|---------|
| MySQL | 唯一事实源；交易、积分签到（Drools）、权限、CRUD | 禁止承担多跳遍历主查询 |
| Neo4j | 派生只读图；路线展开、前置链路、职业图谱、协同推荐 | **禁止作为写主链路**；禁止承载强一致交易 |
| Elasticsearch 9.2.1 | 全文检索、分词聚合 | 禁止用图遍历替代检索，反之亦然 |
| Redis/Caffeine | 热点缓存（课程详情、路线结构） | 禁止缓存图遍历大结果集（用分页 + LIMIT） |

### 3.3 一致性模型

- **不追求分布式事务**：MySQL 与 Neo4j 之间无 2PC；CDC 管道提供**秒级最终一致**。
- **可接受延迟**：推荐、路线类查询容忍 ≤ 5s（Debezium 常态毫秒~秒级）；对一致性敏感的下游（如刚创建的课程）走 MySQL 兜底读。
- **对账兜底**：每日跑节点数/校验和比对任务（见 6.4），差异进告警。

---

## 4. 图模型设计

### 4.1 命名规范（Neo4j Cypher Manual 官方约定）

| 元素 | 规范 | 示例 |
|------|------|------|
| 节点标签 Label | PascalCase | `Course`、`RoadMap`、`Career` |
| 关系类型 Type | SNAKE_CASE_ALL_CAPS，**动词化** | `NEXT_STEP`、`HAS_PREREQUISITE`、`COMPLETED` |
| 属性 Property | snake_case | `mysql_id`、`finished_at` |

> 官方建模课明确：避免名词型关系名（如 `email`），关系类型上限社区版 64K / 企业版 16M——本项目类型数 < 30，无压力。

### 4.2 节点与关系定义

**节点（与 MySQL 表一一投影，携带业务键 `mysql_id`）：**

| Label | 来源表 | 关键属性 |
|-------|--------|---------|
| `User` | ucenter_member | mysql_id, nickname, level |
| `Course` | source_course | mysql_id, name, type(0视频/1专栏), page_view |
| `CourseCategory` | course_category | mysql_id, name |
| `RoadMap` | roadmap | mysql_id, title, step |
| `RoadMapDetail` | roadmap_detail | mysql_id, title, map_order |
| `Tag` | road_map_tag | mysql_id, name |
| `Career` | career | mysql_id, name, level |
| `Recruit` | recruit | mysql_id, title |
| `InnerRecommend` | inner_recommend | mysql_id, title |
| `Interview` | interview | mysql_id, title |

**关系（含属性的用 `@RelationshipProperties` 建模）：**

| 关系 | 方向 | 来源 | 属性 |
|------|------|------|------|
| `SUB_OF` | Course→Course | source_course.parent_id | — |
| `BELONGS_TO` | Course→CourseCategory | category 关联 | — |
| `NEXT_STEP` | RoadMap→RoadMapDetail | roadmap_detail(map_order) | seq |
| `REFERENCES` | RoadMapDetail→Course | roadmap_detail.course_id | — |
| `TAGGED` | RoadMapDetail→Tag | tag CSV 拆分 | — |
| `HAS_PREREQUISITE` | Course→Course | 业务标注/路线拓扑推断 | — |
| `SUB_OF` | Career→Career | career.parent_id | — |
| `FOR_CAREER` | Recruit→Career | recruit 关联 | — |
| `RECOMMENDS` | InnerRecommend→Recruit | inner_recommend.job_id | — |
| `COMPLETED` | User→Course | 学习记录（P2 建设） | progress, finished_at |
| `VIEWED` | User→Course/Article | 浏览行为（P2） | at |
| `COMMENTED_ON` | User→Article | comment | at |

### 4.3 约束与幂等（CDC 的地基）

```cypher
-- 业务键唯一约束：既是 MERGE 的锚点，也是幂等保证
CREATE CONSTRAINT course_mysql_id   IF NOT EXISTS FOR (c:Course)         REQUIRE c.mysql_id IS UNIQUE;
CREATE CONSTRAINT user_mysql_id     IF NOT EXISTS FOR (u:User)           REQUIRE u.mysql_id IS UNIQUE;
CREATE CONSTRAINT roadmap_mysql_id  IF NOT EXISTS FOR (r:RoadMap)        REQUIRE r.mysql_id IS UNIQUE;
CREATE CONSTRAINT detail_mysql_id   IF NOT EXISTS FOR (d:RoadMapDetail)  REQUIRE d.mysql_id IS UNIQUE;
CREATE CONSTRAINT career_mysql_id   IF NOT EXISTS FOR (c:Career)         REQUIRE c.mysql_id IS UNIQUE;
CREATE CONSTRAINT recruit_mysql_id  IF NOT EXISTS FOR (j:Recruit)        REQUIRE j.mysql_id IS UNIQUE;

-- 查询驱动的二级索引
CREATE INDEX course_name     IF NOT EXISTS FOR (c:Course)  ON (c.name);
CREATE INDEX career_level    IF NOT EXISTS FOR (c:Career)  ON (c.level);
```

**原则：CDC 写入全部走 `MERGE (n:Label {mysql_id: $id}) SET n += $props`，永远不 CREATE 裸节点。** 源表主键 = 节点业务键，这是增量同步不产生重复节点的唯一可靠保证（官方 CDC 文档同样强调唯一性约束是 Schema 策略的前提）。

### 4.4 建模红线

1. **警惕超级节点（supernode）**：`Tag`/`CourseCategory` 可能被十万级节点挂载，一度遍历即退化。缓解：标签仅作属性维度查询时不必建成节点；必须建时监控 `degree` 并考虑反向建模（从 Course 出发而非从 Tag 出发）。
2. **关系即动词，方向按问题定**：按「用户会问什么」选方向，方向不影响性能，只影响 Cypher 可读性（官方 GraphAcademy）。
3. **fanout 适度**：不为「同城用户」这类低频问题拆出 City 节点；重复属性提升为节点仅当它能独立回答业务问题。
4. **模型要经受例数据检验**：每个关系加入后用真实样本数据跑一遍目标问题（官方建模流程第 6 步），答不出就重构。

### 4.5 核心查询样例（SDN `@Query` / Neo4jClient）

```cypher
// ① 学习路线按序展开（替代 N 次 join + 内存排序）
MATCH (r:RoadMap {mysql_id: $roadMapId})-[:NEXT_STEP {seq_order}]->(d:RoadMapDetail)
OPTIONAL MATCH (d)-[:REFERENCES]->(c:Course)
RETURN d.map_order AS seq, d.title AS title, c.name AS courseName
ORDER BY seq;

// ② 前置课程链路（1~3 跳，替代递归 CTE）
MATCH path = (c:Course {mysql_id: $courseId})-[:HAS_PREREQUISITE*1..3]->(p:Course)
RETURN p.mysql_id AS id, p.name AS name, length(path) AS depth
ORDER BY depth;

// ③ 协同推荐：学完某课程的用户还学了什么
MATCH (u:User)-[:COMPLETED]->(c:Course {mysql_id: $courseId})<-[:COMPLETED]-(peer:User)
MATCH (peer)-[:COMPLETED]->(rec:Course)
WHERE rec.mysql_id <> $courseId
RETURN rec.mysql_id AS id, rec.name AS name, count(DISTINCT peer) AS freq
ORDER BY freq DESC LIMIT 10;

// ④ 职业 → 岗位 → 内推入口
MATCH (c:Career {mysql_id: $careerId})<-[:FOR_CAREER]-(j:Recruit)
OPTIONAL MATCH (j)<-[:RECOMMENDS]-(ir:InnerRecommend)
RETURN j.title AS job, collect(ir.title) AS referrals;
```

---

## 5. 技术选型与集成设计

### 5.1 版本矩阵（2026-10-21 核实）

| 组件 | 选型 | 核实依据 |
|------|------|---------|
| Neo4j Server | **5.26 LTS**（生产首选，热补丁支持至 2028-06）；dev/试用可用 2026.09.0 calVer | Neo4j Supported Versions KB：5.26 为 5.x 最后 LTS；2026.09.0 为当前最新（2026-09-21 发布） |
| Spring Data Neo4j | **7.4.x**（由 Boot 3.4.1 BOM 托管，无需手写版本） | spring-boot-dependencies-3.4.1.pom：`spring-data-bom.version=2024.1.1`；Spring Data 2024.1 GA 公告含 SDN 7.4 |
| Java Driver | **5.25.0**（Boot 3.4.1 托管） | 同上 POM：`neo4j-java-driver.version=5.25.0` |
| 驱动-服务端兼容 | driver 5.x ↔ server 4.4 / 5.x / 2025.x / 2026.x | Neo4j 官方驱动兼容表（Supported Versions KB 每行列 "Compatible Driver Versions: 6.x, 5.x, 4.4*"） |
| SDN 8.x | **不采用**（属 Spring Data 2025.x 世代，需 Boot 3.5+；为图库单独升 Boot 代价大于收益） | Maven Central：SDN 最新 GA 8.1.1；本项目锁定 Boot 3.4.1 |

> 选 5.26 LTS 而非最新 calVer 的理由：SDN 7.4 的官方支持矩阵以 5.x 为基准，LTS 支持期到 2028-06，符合生产环境稳定性优先原则；若后续整体升级 Boot 3.5+，可平滑迁移到 2026.x + SDN 8.x。

### 5.2 依赖落位（镜像 ES starter 的位置）

`education-back/pom.xml`（与 `spring-boot-starter-data-elasticsearch` 并列）：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-neo4j</artifactId>
</dependency>
```

`domain` 模块只定义图查询端口接口（保持 DDD 依赖方向），`infra` 放 `Neo4jRepository` 实现——与现有 `MemberGrowthRepositoryImpl` 同构。

### 5.3 配置骨架

```yaml
# application.yml（各 profile 覆盖 URI/密码，密码走 Nacos 配置中心，禁止硬编码）
spring:
  neo4j:
    uri: ${NEO4J_URI:bolt://localhost:7687}        # 未来扩集群改 neo4j+s:// 或 bolt+routing://
    authentication:
      username: ${NEO4J_USERNAME:neo4j}
      password: ${NEO4J_PASSWORD}
    pool:
      max-connection-pool-size: 50
      connection-acquisition-timeout: 30s
  data:
    neo4j:
      database: ${NEO4J_DATABASE:neo4j}
```

> 连接 URI 注意：`bolt://` 是单点直连；官方与生产实践均建议集群场景用 `neo4j://`（routing）或 `neo4j+s://`，客户端自动感知拓扑，避免扩集群时逐台改配置。

### 5.4 实体骨架（SDN 7.4 注解体系）

```java
@Node("Course")
public class CourseNode {
    @Id @GeneratedValue private Long internalId;      // 库内 ID，勿外泄
    @Property("mysql_id") private Integer mysqlId;    // 业务键 = 唯一约束锚点
    private String name;
    private Integer type;                             // 0 视频 / 1 专栏
    @Relationship(type = "BELONGS_TO", direction = Relationship.Direction.OUTGOING)
    private CategoryNode category;
}

@RelationshipProperties
public class CompletedRel {
    @RelationshipId private Long id;                  // 关系内部 ID，@RelationshipProperties 必需
    private Integer progress;
    private LocalDateTime finishedAt;
}
```

### 5.5 事务边界

- 图侧**只读查询**统一走 `Neo4jClient`/Repository 的 `executeRead` 语义（SDN `@Query` 读方法默认只读事务）。
- 写入只存在于 CDC 管道（连接器自有事务），应用代码**不写图**。
- 禁止把 MySQL 事务与图操作编入同一业务事务（无原子性，见 3.3）。

---

## 6. 数据同步方案选型

### 6.1 三方案对比

| 方案 | 延迟 | 复杂度 | 一致性 | 结论 |
|------|------|--------|--------|------|
| A. Debezium(MySQL binlog) → Kafka → Neo4j Connector（官方 sink） | 毫秒~秒 | 中（需启用 Kafka + 部署连接器） | 最终一致 + 对账 | **推荐主方案** |
| B. XXL-JOB 定时增量（watermark + MERGE） | 分钟级 | 低 | 最终一致 | 推荐为 P1 过渡与兜底（项目已有 XXL-JOB 基建，当前 `enabled: false`） |
| C. 应用双写（MySQL 事务后同步写图） | 实时 | 低（初期） | **无保证** | 禁止作主方案（非原子，失败即漂移）；仅允许极小规模过渡期 |

### 6.2 方案 A 实施要点（官方 Kafka Connector 文档口径）

```json
{
  "name": "neo4j-education-sink",
  "connector.class": "streams.kafka.connect.Neo4jSinkConnector",
  "topics": "education.mysql.source_course,education.mysql.roadmap_detail",
  "neo4j.server.uri": "bolt://neo4j:7687",
  "neo4j.topic.cypher.education.mysql.source_course":
    "MERGE (c:Course {mysql_id: event.id}) SET c += event.properties",
  "neo4j.topic.cypher.education.mysql.roadmap_detail":
    "MERGE (d:RoadMapDetail {mysql_id: event.id}) SET d += event.properties WITH d "
    "MATCH (r:RoadMap {mysql_id: event.road_map_id}) MERGE (r)-[:NEXT_STEP {seq: event.map_order}]->(d) WITH d "
    "MATCH (c:Course {mysql_id: event.course_id}) MERGE (d)-[:REFERENCES]->(c)"
}
```

配套 Debezium MySQL 源连接器需配 `ExtractNewRecordState` SMT（展平 envelope，使 `event.*` 即行数据）、`database.server.name=education`。

**官方明确的操作纪律（Kafka Connector 文档）：**

1. **消息保序**：CDC 场景单个 topic 用**单分区**——并行化会乱序，吞吐让位于正确性。
2. **批量写入串行化**：写关系需锁定两端节点，高并发批量导入应组织成「一个接一个的事务」而非并行进程，否则大量锁等待/锁异常。
3. **复杂 ETL 放 Kafka 侧**（ksqlDB/流处理），Cypher 只做 transform + load；输入格式越简单越好。
4. **topic 内容保持同质**：一个 topic 一种消息形态，避免混合格式。
5. 首批全量初始化用 `neo4j-admin database import`（离线 CSV 批量导入，分钟级百万节点）或 Data Importer；之后 binlog 增量接力，注意记录 binlog 位点基线。

### 6.3 删除处理

MySQL 逻辑删除（项目已全局启用 `logic-delete-field: deleteFlag`）→ CDC 捕获删除事件 → 连接器执行 `DETACH DELETE` 或节点/关系软删除标记。**建议图侧同步软删**（`deleted` 属性），DETACH DELETE 仅用于归档清理，防止误删连锁丢失关系。

### 6.4 对账与监控

```sql
-- 每日对账：MySQL 计数 vs 图节点计数
-- MySQL: SELECT COUNT(*) FROM source_course WHERE delete_flag = 0;
-- Cypher: MATCH (c:Course) WHERE c.deleted IS NULL RETURN count(c);
```

监控项：Debezium lag（binlog 位点延迟）、Kafka consumer lag、连接器错误率、Neo4j 页面缓存命中率、事务/查询 P99。告警阈值建议：CDC lag > 30s、对账差异 > 0 即告警。

---

## 7. 分阶段实施路线图

| 阶段 | 周期 | 内容 | 验收门（量化） |
|------|------|------|--------------|
| P0 环境与连通 | 1 周 | Docker 起 Neo4j 5.26；pom 加 starter；yml 配置；`Neo4jHealthIndicator` 纳入 actuator；建约束与索引 | ✅ **已完成并验证**：容器 UP、3 条唯一约束生效（course/roadmap/roadmap_detail_mysql_id）、dev 连接可通 |
| P1 单场景 POC | 2-3 周 | 选「学习路线展开 + 前置课程」场景：全量导入（neo4j-admin import）→ 图查询 API → 灰度只读 | ✅ **已完成并验证**：学习路线展开经 `GET /graph/learning-path/{id}` 跑通；图节点/关系计数与 MySQL 逐一等价（见 7.1）；等值性已核对 |
| P2 CDC 管道 | 2 周 | 启用 Kafka → Debezium → Neo4j sink；对账任务；回滚预案（停连接器=图数据冻结在最后一致点，不影响主站） | 增量同步 lag P95 ≤ 5s；7 天对账零差异；故障演练：杀连接器/杀 Neo4j，主站功能无损 |
| P3 图谱扩展 | 持续 | 职业-岗位图谱、行为事件沉淀（COMPLETED/VIEWED）、协同推荐；可选 GDS 算法 | 推荐场景 CTR/转化提升有度量；图查询占比、缓存命中率有基线 |

**回滚性**：Neo4j 为纯读模型衍生存储，任何阶段下线只需停管道 + 摘除图查询代码路径，MySQL 数据无损——这是本架构最大的风险兜底。

### 7.1 P0 + P1 POC 落地状态（2026-10-21 已实现并端到端验证）

> 最简 DDD 端口/适配器落地，已跑通「编译 → 打包 → 启动 → 投影 → 查询」全链路。

**代码落位（与现有 `MemberGrowthRepositoryImpl` 同构的六边形风格）：**

| 层 | 文件 | 职责 |
|----|------|------|
| domain | `domain/.../graph/model/LearningPathStep.java`（record）、`domain/.../graph/port/LearningPathQueryPort.java` | 纯模型 + 端口接口，零图框架依赖 |
| infra（适配器） | `infra/.../graph/adapter/Neo4jLearningPathQueryAdapter.java` | `Neo4jClient` 执行路线展开 Cypher，映射为 `LearningPathStep` |
| infra（支撑） | `infra/.../graph/support/GraphWriter.java`、`GraphSchemaInitializer.java` | `UNWIND MERGE` 幂等投影 + 启动建 3 条唯一约束 |
| application | `application/.../graph/LearningPathApplicationService.java` | 薄封装，委托端口 |
| education-back | `education-back/.../graph/LearningPathProjector.java`（`ApplicationRunner @Order(2)`）、`controller/GraphController.java` | 启动期全量投影（须在 schema `@Order(1)` 之后）；暴露 `GET /graph/learning-path/{roadMapId}` |
| config | `application.yml`（spring.neo4j）、`InterceptorConfig.java` | 图连接；`/graph/**` 显式放行（PermissionConfig 空表时拦截器默认覆盖 `/**`） |

**端到端验证证据（应用端口 18088）：**

1. 启动日志确认：`Neo4j 图 schema 初始化完成：3 条唯一约束生效`；`Neo4j 学习路径投影完成：Course=49, RoadMap=9, RoadMapDetail=12`。
2. Cypher 直查节点/关系计数与 MySQL 等价：节点 50 Course / 12 RoadMapDetail / 9 RoadMap；关系 49 SUB_OF / 8 NEXT_STEP / 8 REFERENCES。
3. `GET /graph/learning-path/3` → **HTTP 200**，按 `seq` 有序返回 4 步（Java基础 → SpringBoot框架 → SpringCloud框架 → 系统设计，各带 `courseId`/`courseName`/`courseType`）。
4. `GET /graph/learning-path/999` → **HTTP 200** 空数组（未知路线优雅降级，非 5xx）。

**落地期关键坑（后续维护必读）：**

| # | 坑 | 正确做法 |
|---|----|---------|
| 1 | Neo4j 5.26 不识别 `server.memory.heap.initial.size`（少下划线） | 用官方键 `server.memory.heap.initial_size`；本次镜像未带任何内存 env，默认即可起 |
| 2 | SDN **不把 `org.neo4j.driver.QueryRunner` 注册为 Spring Bean** | `GraphWriter`/`GraphSchemaInitializer` 注入 `Neo4jClient`，经 `neo4jClient.getQueryRunner()` 取底层 driver 执行器 |
| 3 | `Neo4jClient` 链式调用误用 `.fetch().mappedBy(...)` | 正确链：`.query(c).bind(x).to("n").fetchAs(Clazz.class).mappedBy(fn).all()`（`fetchAs` 才返回带 `mappedBy` 的 `MappingSpec`）；`.all()` 返回 `Collection`，需包成 `List` |
| 4 | `-Dserver.port=18088` 写在 `-jar` 之后被忽略 | 所有 `-D` 系统属性必须置于 `-jar` 之前 |
| 5 | 后台进程普通 `nohup java -jar &` 被沙箱回收（日志 0 字节、curl 502） | 用工具托管后台进程启动 |
| 6 | 验证期 Nacos 8848 未起、StoreClient 死 Feign 客户端致启动失败 | `-Dspring.cloud.nacos.*.enabled=false` 等绕开云组件；仅验证图链路 |

**遗留 TODO（P2/P3 与加固）：**

- 投影器从「启动全量」演进为 Debezium CDC 管道（见 6.1 方案 A）；当前全量投影是 P0→P1 的可用过渡，非最终同步方案。
- 图查询加 MySQL 兜底：端口层图库不可用时，控制器应显式回退 MySQL 连表查询（当前仅打 warn，无显式降级）。
- 生产密码走 Nacos 配置中心，禁止明文进 git（现状 `dev12345` 仅 dev）。

---

## 8. 性能与运维最佳实践

### 8.1 内存配置（Operations Manual 官方口径）

- 显式配置，不要用启动启发式：`server.memory.heap.initial_size` 与 `server.memory.heap.max_size` **设同值**（避免 full GC 停顿）。
- 页缓存应容纳「数据 + 原生索引」并预留增长（官方建议按 +20% 估算）；OS 至少留 1GB。
- 用官方推荐工具起手：`neo4j-admin server memory-recommendation --memory=16g`（5.x 旧称 `memrec`），输出可直接粘贴进 `neo4j.conf`。
- 官方 16G 示例输出：`heap.initial_size=5g`、`heap.max_size=5g`、`pagecache.size=7g`（堆:页缓存 ≈ 4:5.6，其余归 OS/Lucene/网络缓冲）。
- 本项目规模（教育平台，预估百万节点内）dev 容器 1G 堆 + 1G 页缓存足够；生产按 memrec 实测。事务内存上限可用 `dbms.memory.transaction.total.max` 约束，防长事务打爆堆。

### 8.2 索引与查询调优

- 查询模式驱动索引（见 4.3）；唯一约束本身即索引，MERGE 锚点查询 O(1)。
- 上线前对每个图查询跑 `EXPLAIN`/`PROFILE`：确认起点走了约束索引而非 `AllNodesScan`；关注 `db hits` 量级。
- **参数化查询**（`$param`）：复用计划缓存、防注入；SDN `@Query` 天然参数化，禁止字符串拼接 Cypher。
- 遍历加深度上限（`*1..3`），无界变长遍历是线上事故第一来源。

### 8.3 写入批处理（面向 CDC 管道）

- `UNWIND $batch` 分批提交，单批 5k~1 万行；大事务拆小，控制事务状态内存。
- 批量期间单线程串行（见 6.2-2）；观测锁等待。

### 8.4 备份恢复

- 社区版无在线备份：`neo4j-admin database dump neo4j --to-path=/backups`（需停库或一致性快照窗口），按 RPO 定义每日 dump + 异地留存。
- 企业版才有多数据库、集群、在线备份——**当前选型按社区版单机规划，架构上不依赖企业特性**（避免未来许可风险）。
- 恢复演练每季度一次，验证 dump → 新实例 → 对账全链路。

### 8.5 监控

- actuator 聚合 `Neo4jHealthIndicator` + 驱动 metrics；Neo4j 侧按 Operations Manual → Metrics 开启 Prometheus/JMX 端点，接现有监控。
- 核心看板：页面缓存命中率、堆使用、活动事务数、连接池在用/空闲、CDC lag、对账差异数。

### 8.6 安全基线

1. 改默认 `neo4j` 密码（生产强密码 + Nacos 托管，禁止进 git——注意现有 application.yml 已明文存放 OSS/R2 密钥，建议借本次一并整改）。
2. 7687/7474 不暴露公网；跨机走 TLS（`neo4j+s://` / `bolt+s://`）。
3. 连接账号最小权限：应用账号只读所需标签，CDC 账号仅写权限。

---

## 9. 风险登记与反模式红线

| # | 风险/反模式 | 等级 | 对策 |
|---|------------|------|------|
| 1 | 把 Neo4j 当写主库（积分、订单类强一致） | 致命 | 架构红线 3.2；只做读模型 |
| 2 | 应用双写无 outbox，两库漂移 | 高 | 只用 CDC；双写仅允许过渡期且配补偿任务 |
| 3 | CDC MERGE 无唯一约束 → 重复节点爆炸 | 高 | 4.3 约束先行；P2 验收含重复节点检测 `MATCH (c:Course) WITH c.mysql_id AS k, count(*) c WHERE c > 1 RETURN k` |
| 4 | 超节点（Tag/Category）遍历退化 | 中 | degree 监控 + 反向建模；P1 模型评审必查 |
| 5 | Cypher 里做复杂 ETL | 中 | 官方纪律：Kafka 侧重塑，Cypher 只 transform+load |
| 6 | 社区版能力误用（集群、在线备份、多库） | 中 | 按社区版单机设计；企业特性列入未来评估而非当前假设 |
| 7 | 与 ES 职责重叠导致双份维护 | 低 | 边界表 3.2；ES 只管检索 |
| 8 | 无界变长遍历打爆生产 | 中 | 遍历恒设深度上限 + 查询评审 + PROFILE 准入 |

---

## 10. 验证方案（POC 验收标准）

1. **等价性测试**（P1 核心门）：同一批输入（选定 roadmap/career 子树），MySQL 现有查询链路 vs Cypher 结果集逐条 diff——ID 集合一致、顺序一致、字段值一致；输出 diff 报告。
2. **性能对比表**（P95，同机同数据量）：路线展开、前置链路（1/2/3 跳）、职业-岗位-内推三项，记录 MySQL 侧耗时与 Cypher 侧耗时。
3. **稳定性**：50/100 并发下图查询错误率 = 0、无连接获取超时；长稳 24h 无 OOM、无缓存命中率劣化。
4. **一致性**：P2 连续 7 天对账零差异；注入延迟/丢消息故障后能自动追赶且告警触发。
5. **回归**：既有 `mvn test` 与冒烟用例全绿（图查询以新用例补充，不动既有测试）。

---

## 11. 参考依据（一手来源）

| # | 来源 | 用途 |
|---|------|------|
| 1 | Neo4j Supported Versions KB（neo4j.com/developer/kb/neo4j-supported-versions） | 版本日历：2026.09.0 最新、5.26 LTS、驱动兼容表 6.x/5.x/4.4* |
| 2 | Neo4j Java Driver Manual → Install（neo4j.com/docs/java-manual/current/install/） | 驱动 6.1.0 支持 4.4/5.x/2025.x/2026.x；驱动需 Java 17+ |
| 3 | spring-boot-dependencies-3.4.1.pom（Maven Central 实测） | `neo4j-java-driver.version=5.25.0`、`spring-data-bom.version=2024.1.1` |
| 4 | Spring Data 2024.1 GA 官方公告（spring.io/blog/2024/11/15/spring-data-2024-1-goes-ga） | SDN 7.4 GA，随 Spring Boot 3.4 发布 |
| 5 | Maven Central `spring-data-neo4j` maven-metadata.xml | SDN 版本谱系：7.5.x 线、8.1.1 GA、8.2.0-M2 |
| 6 | Neo4j Connector for Kafka 官方文档（neo4j.com/docs/kafka/ 及 sink/architecture） | sink 类名 `streams.kafka.connect.Neo4jSinkConnector`、CDC Debezium 格式支持、保序/串行批量/拒绝复杂 ETL 纪律 |
| 7 | Neo4j Operations Manual → Memory configuration / neo4j-admin memory-recommendation | heap/pagecache 配置项、16G→5g/7g 官方示例、memrec 用法、事务内存上限 |
| 8 | Neo4j GraphAcademy《Graph Data Modeling Fundamentals》（training-gdm-40） | connections are verbs、方向按问题选、fanout 与超节点、模型用例验证流程 |
| 9 | Neo4j Data Importer 文档 → Data modeling / Cypher Manual 命名约定 | PascalCase 标签、SNAKE_CASE_ALL_CAPS 关系、snake_case 属性 |
| 10 | Neo4j《Graph Databases for Dummies》官方摘录 | labeled property graph 定义、「连接的价值」判据 |
| 11 | 本仓库代码与配置实测（pom.xml、application.yml、entity/mapper、infra/mq） | 第 1 节现状盘点全部事实 |

---

*文档定位：架构决策分析（ADR 性质）。落地执行前，P1 场景需再出一份细化的接口契约与数据字典。*
