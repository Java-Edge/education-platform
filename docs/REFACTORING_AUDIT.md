# education-platform 重构审计与演进路线

> 审计日期：2026-09-07
> 审计范围：`/Users/javaedge/soft/IDEAProjects/education-platform`（5 个 Maven 模块，377 个 Java 文件）
> 审计目标：① 统一到 JDK 21 LTS；② 高版本框架适配；③ 把 LangChain4j 从"能跑通"推进到"工程级实践"
> 数据来源：本地文件逐一核验 + Maven Central / endoflife.date / Oracle Java SE Support Roadmap 官方版本数据

---

## 0. 结论摘要

| 维度 | 现状判定 | 证据 |
|---|---|---|
| JDK 基线 | **存在硬性矛盾，构建产物无法运行** | 编译 `release=23`，但 Dockerfile 与 GitHub Actions 均为 JDK 21 |
| 框架基线 | **Spring Boot 3.4 已于 2025-12-31 OSS EOL** | endoflife.date/spring-boot |
| AI 技术栈 | **落后一个大版本 + 停留在最原始调用层次** | LangChain4j 0.36.2 → 现任 1.17.2；项目仅用 `ChatModel.generate()`，无 AiServices/Memory/RAG/Tool/Streaming |
| 架构分层 | **形似 DDD，实为单层三层架构** | domain 19 类 / application 8 类 / education-back 231 类，95% 业务堆在启动模块 |
| 依赖治理 | **存在 Boot 3 不兼容 + 0 使用 + 远古版本三类包袱** | springfox 3.0.0（0 引用）、Gateway 死代码、ShardingSphere 4.1.1（2019） |
| 代码质量 | **JDK 8 写法为主，现代语法零使用** | record / var / sealed / Optional / 虚拟线程显式用法均为 0 |
| 安全合规 | **生产密钥明文入库** | OSS AK/SK、Cloudflare R2 Secret、ES 密码硬编码于 `application.yml` |

**总判断**：当前代码处于"框架版本已经升到 Boot 3，但编码范式仍停在 JDK 8 + 单体三层"的中间态。这个中间态恰好是重构收益最高的位置——升级成本低、收益面大，且 LangChain4j 部分几乎是空白画布。

---

## 1. 致命问题（P0，必须先修）

### 1.1 JDK 版本自相矛盾 —— 编译 23，运行 21

```
pom.xml:  <maven.compiler.release>23</maven.compiler.release>   → class 文件 major version 67
Dockerfile: FROM bubaiwantong/openjdk:21-jdk-alpine              → JVM 最高支持 major 65
.github/workflows/maven.yml: java-version: '21'
```

**后果**：Docker 中运行必然抛 `UnsupportedClassVersionError`；CI 用 JDK 21 编译则直接编译失败（`release=23` 需要 JDK 23+）。二者不可能同时对。

**JDK 23 状态**（Oracle Java SE Support Roadmap）：
- GA 2024-09-17，**Premier Support 已于 2025-03-18 结束（EOL）**，无 Extended Support
- 非 LTS，被 JDK 24 取代

**JDK 21 状态**：LTS，2023-09-19 GA，Premier Support 至 **2028-09-30**，Extended 至 2031-09-30 —— 作为本项目基线完全合理。
（附注：JDK 25 亦为 LTS，2025-09-16 GA 支持至 2030-09-30；若后续想长期免迁移可考虑，但 21 已足够，且 Spring Boot 4.x 对 17–26 全支持。）

**修复动作**：
1. `pom.xml`：`java.version` / `maven.compiler.release` / `compilerVersion` 三处统一为 `21`
2. `infra/pom.xml` 硬编码的 `<source>23</source><target>23</target>` 一并改为 21（当前绕过了父 pom 统一管理）
3. 根 pom `maven-compiler-plugin` 建议改用 `<release>${java.version}</release>` 单参数，避免 `source/target/release` 三处漂移
4. 本机当前安装了 8 / 11 / 23.0.2 / 24 / 25，**没有 JDK 21**，需先安装（Temurin 21 或 Corretto 21）

### 1.2 生产密钥明文入库

`education-back/src/main/resources/application.yml` 中明文且已提交 git：

| 行 | 密钥 | 类型 |
|---|---|---|
| L51 | `HYnyGbJ7LOXVUN*8XByl` | Elasticsearch 密码 |
| L145-147 | `LTAI5tM11FARgMsCGjaYtCQk` / `g80zES4Xygyrx4stk2XhEYeV0xRSiK` | 阿里云 OSS AK/SK（**重复定义两遍**：`oss.*` 与顶层 `accessKeyId`） |
| L156-159 | Cloudflare R2 access-key / secret-access-key | R2 对象存储 |

**修复动作**：
1. 立即轮换上述全部凭据（已提交公开仓库的密钥视为已泄露）
2. 改为 `${OSS_ACCESS_KEY_ID:}` 环境变量占位 + `.env` / Nacos 配置中心
3. `.gitignore` 已忽略 `**/.env`，但没有忽略 `application.yml` 本体 —— 考虑把环境相关配置拆到 `application-{env}.yml` 并忽略

### 1.3 生产环境 Hibernate 自动改表

```yaml
spring:
  profiles:
    active: prod          # 默认激活 prod
  jpa:
    hibernate:
      ddl-auto: update    # Hibernate 启动时自动 ALTER TABLE
```

而项目真正的 ORM 是 MyBatis-Plus。`spring-boot-starter-data-jpa` 被引入后 Hibernate 会扫描全部 `@Entity` 并改表，属于高危配置。

**修复动作**：`ddl-auto` 改为 `none`（或 `validate`）；若无需 JPA，**直接移除 `spring-boot-starter-data-jpa` 依赖**（`application/pom.xml`、`education-back/pom.xml` 各有一处），schema 演进交给 Flyway/Liquibase。

### 1.4 `LOG_PATH_IS_UNDEFINED` 污染仓库

`logback-spring.xml:20`：
```xml
<springProperty scope="context" name="LOG_PATH" source="log.filepath"/>
```
`application.yml` 从未定义 `log.filepath`，且 `logging.file.name` 被注释。LOG_PATH 解析为空 → logback 以字面量 `LOG_PATH_IS_UNDEFINED` 建目录，项目根已生成 **14 个日志文件**。

**修复动作**：在 yml 增加 `log.filepath: logs/education-platform`，或给 `<springProperty>` 加 `defaultValue`。同时清理仓库根目录的 `LOG_PATH_IS_UNDEFINED/`。

### 1.5 Actuator 全量暴露

```yaml
management.endpoints.web.exposure.include: '*'
```
配合 1.2 的明文密钥，等于把配置后门开在公网。改为 `health,info,metrics,prometheus`。

---

## 2. 框架版本对齐（P1）

### 2.1 版本现状对照表

| 组件 | 项目当前 | 建议目标 | 说明 |
|---|---|---|---|
| Spring Boot | 3.4.1 | **4.1.1**（2026-08-21）或过渡 3.5.16 | 3.4 分支 OSS 支持 2025-12-31 终止 |
| Spring Cloud | 手写 `spring-cloud-*` 3.1.2 + Alibaba 2023.0.3.2 | **2025.1.x (Oakwood)**，配套 Boot 4.0/4.1 | 现写法把 gateway/loadbalancer/openfeign 钉在 3.1.2（Boot 3.0 时代），与 Boot 3.4 已错配，靠 `compatibility-verifier.enabled: false` 强行压掉告警 |
| LangChain4j | 0.36.2 | **1.17.2**（2026-07） | 跨 1.0 breaking change，见 §4 |
| MyBatis-Plus | `mybatis-plus-boot-starter` 3.5.3.1 | `mybatis-plus-spring-boot3-starter` **3.5.17**（2026-07-09） | 坐标与版本都需更新；Boot 4 则用 `mybatis-plus-spring-boot4-starter` |
| ShardingSphere | `sharding-jdbc-core` / `sharding-jdbc-orchestration-spring-boot-starter` 4.1.1（2019） | 5.x `shardingsphere-jdbc`（**或先整体移除**） | 4.x 与 5.x 完全重构；当前项目无实际分库分表配置，建议先移除 |
| Seata | 1.5.2 | 与 ShardingSphere 同上 | 无实际使用，建议移除 |
| Kafka client | `kafka-clients` 2.3.0（2019） | `spring-kafka` 管理版本，去掉手写 version | 2.3.0 与 Boot 3.4 的 spring-kafka 3.x 不兼容 |
| fastjson | 1.2.75（11 个文件） | **fastjson2 2.x** 或统一到 Jackson | 1.x 已停更且反序列化漏洞家族密集；项目同时有 12 个文件用 Jackson，双栈并存 |
| springfox | 3.0.0 | **删除** | SpringFox 最后版本，仅支持 Boot 2 + javax，**0 处代码引用**，纯负担 |
| springdoc | 2.8.3 | 保持 / 随 Boot 4 升级 | 已是正确选择 |
| Drools | 9.44.0.Final | 核实当前 10.x/11.x | 9.44 发布于 2023 年；升级前先确认 `kie-ci` 在 Boot 4 的可用性 |
| Docker 基础镜像 | `bubaiwantong/openjdk:21-jdk-alpine` | `eclipse-temurin:21-jre-jammy` | 第三方非官方镜像 + alpine 在部分 JNI/字体场景有坑 |
| GitHub Actions | checkout@v2 / setup-java@v2 / cache@v2 / login-action@v1 / build-push-action@v2 | 全部升 v4/v3 | v1/v2 基于已废弃的 Node 12/16 运行时 |

### 2.2 Boot 3.4 → 4.x 的取舍

Spring Boot 4.0（2025-11-20 GA）是基于 Spring Framework 7 的 major 版本，**Java 基线仍是 17**，支持到 Java 26 —— 所以与"JDK 21"目标不冲突。

| 方案 | 优点 | 风险 |
|---|---|---|
| **A. 直接上 Boot 4.1.1** | 一步到位，OSS 支持至 2027-07-31；模块化瘦身、HTTP Service Client、API 版本化、OpenTelemetry starter | Jackson 3（Jackson 2 仅 deprecated 兼容）、Hibernate 7、Tomcat 11、Spring Security 7；ShardingSphere 4.x / Drools 9.44 / MyBatis-Plus-join 1.1.8.1 均需验证；自动配置类内部 API 不再公开 |
| **B. 先 3.5.16 过渡再 4.x** | 风险可控；3.5 是 3.x 最后一版，迁移路径官方有文档 | 3.5 已于 2026-06-30 OSS EOL，等于刚升级就 EOL，白做一轮 |

**建议**：本项目是学习/实践型工程而非在线生产系统，**选 A**（直接 Boot 4.1.1 + JDK 21）。同时借这次升级把 §2.1 中不兼容的老依赖（ShardingSphere 4.x、Seata、fastjson 1.x、springfox）**一并移除而不是升级**——它们在项目中零实际使用，移除比适配更划算。

---

## 3. 架构分层重构（P1–P2）

### 3.1 现状：DDD 骨架空转

```
domain/       19 类  ← 仅 dict / file / user 三个演示领域，接口定义齐全但无业务
application/   8 类  ← 仅 DictTypeService / FileApplicationService
infra/        30 类  ← 混合了 asm/ 实验代码、test/ 示例代码、真正的 mysql/redis/oss 仓储
common/       89 类  ← 工具 + 常量 + 异常 + AI + Drools + 3 个 jar
education-back/231 类 ← controller(37) service(31+32) mapper(30) entity(30) vo(14) ... 全部业务在此
```

**判定**：`domain` / `application` / `infra` 是"教科书骨架"，只有一个 `dict` 领域完整走通（Entity → Repository 接口 → infra 实现 → ApplicationService）。真正的 231 个业务类仍在 `education-back` 里按 controller/service/mapper 三层平铺，**且 `education-back` 依赖了 `application` 和 `infra`，但业务代码没有一行使用它们**。

### 3.2 三个可选方向

| 方案 | 动作 | 代价 | 收益 |
|---|---|---|---|
| **A. 务实分层**（推荐） | 放弃"纯 DDD"执念，把 `education-back` 按 adapter / application / domain / infra 四层拆分或至少分包；`domain` 模块下沉真实实体与领域服务；`infra` 承接全部 Repository 实现 | 中，主要是包移动 | 消除"两套架构并存"的认知负担，模块依赖方向变清晰 |
| **B. 纯 DDD 落地** | 建立聚合根、值对象、领域事件、仓储接口；贫血 entity 转充血模型 | 高，231 个类基本重写 | 学习价值最大，但周期以月计，且对 CRUD 为主的在线教育业务属于过度工程 |
| **C. 承认现状** | 删掉空转的 domain/application 骨架，明确这是分层单体 | 低 | 消除误导，但失去架构演进空间 |

**建议 A**：核心判据是项目的业务性质——课程/用户/订单/招聘 CRUD 占比极高，领域逻辑稀薄，B 方案的投入产出比不成立。但要把 `domain` 里已经写好的 `IDomainCRUD` / `IDomainQuery` / `RepositoryFactory` 这套接口**真正用起来**，而不是让它在 `dict` 一个领域里自娱自乐。

### 3.3 模块内必须清理的内容

- `infra/asm/`（MyCustomAgent / MyTransformer / Person）、`infra/test/`（MultiWaiterRestaurant / StringQuartet / Effect）、`infra/mqtt/*Example`、`infra/drools/Demo.java` —— 学习实验代码，应移出或删除
- `common/juc/Main.java` —— main 方法实验代码
- `common/src/main/resources/lib/lombok-1.8.36.jar` 等 **3 个 jar 已提交进 git**（`application/`、`common/`、`education-back/` 各一份），且 pom 已声明 lombok 1.18.38 → 版本冲突隐患，删除
- `education-back/controller/TestRedis.java`、`config/DemoConfig.java` —— 调试残留，含 `System.out.println` 与 `e.printStackTrace()`

### 3.4 死代码与错配依赖

- **`common/filter/IPLimitFilter.java`**：实现 `GlobalFilter`（Spring Cloud Gateway / WebFlux API），但整个应用是 Servlet + Spring MVC，该 Bean **永远不会被执行**；且 `isIpOverLimit()` 直接 `return false`，是未完成的示例。`application.yml` 中的 `exclude-url.ip-limit-urls` 同样无效。→ 删除，或改用 `HandlerInterceptor` + Redis 真正实现
- **`springfox-boot-starter` 3.0.0**：0 处代码引用，且 Boot 3 不兼容 → 删除
- **pagehelper 与 MyBatis-Plus 分页插件** 功能重叠，`application.yml` 两者都配置了 → 保留 MP `PaginationInnerInterceptor`，删 pagehelper
- **`mybatis-plus-generator` + `velocity-engine-core`**：代码生成器，不应进入运行时 classpath → 改 `provided` 或移到独立 generator 模块

---

## 4. LangChain4j 深化（P2–P3，本项目最大增量空间）

### 4.1 现状定位：停留在第 1 层

当前实现（`common/ai/`，共 718 行）本质是：
```
ChatLanguageModel.generate(拼接好的 prompt 字符串) → 正则从回复里抠数字
```
属于 LangChain4j 能力矩阵的最底层。框架真正的核心能力（AiServices、ChatMemory、RAG、Tools、Streaming、结构化输出）**一项未用**。

### 4.2 必须先做的版本迁移（0.36.2 → 1.17.2）

1.0 起有 breaking change，当前代码**编译不过**：

| 项目 | 0.36.2 写法 | 1.x 写法 |
|---|---|---|
| 接口名 | `dev.langchain4j.model.chat.ChatLanguageModel` | `dev.langchain4j.model.chat.ChatModel` |
| 调用方法 | `generate(...)` | `chat(...)` |
| 请求/响应 | `dev.langchain4j.model.output.Response` | `dev.langchain4j.model.chat.response.ChatResponse`（`request` 包同理） |
| Spring 集成 | 手写 `@Bean` 装配 | 官方 `langchain4j-{provider}-spring-boot-starter`，yml 直接配 |

另外 1.x 建议走 `langchain4j-bom` 统一管理版本（项目已引 BOM，但下面每个依赖又各自写了 `<version>`，属于冗余且易漂移）。

### 4.3 逐项待补的能力

| # | 能力 | 现状问题 | 建议做法 |
|---|---|---|---|
| 1 | **ChatMemory** | `AiChatServiceImpl` 用 `ConcurrentHashMap<String, List<ChatMessage>>` 存内存，**无容量上限、无过期、无持久化** → 长会话内存泄漏；重启即丢 | `MessageWindowChatMemory` + `PersistentChatMemoryStore`（Redis 实现）；按 sessionId 分片 |
| 2 | **AiServices 声明式** | 全部手写 `SystemMessage.from()` / `UserMessage.from()` 拼装 | 定义 `interface LearningAssistant { @SystemMessage(...) String chat(@UserMessage String q); }`，`AiServices.create(...)` 代理 |
| 3 | **结构化输出** | `CourseAiServiceImpl.extractCourseIds()` 用 `Pattern.compile("\\d+")` 从自然语言中抠 ID，**兜底硬编码 `List.of(1,2,3)`** —— 这是最脆弱的一环 | AiServices 方法直接返回 `List<CourseRecommendation>` POJO / 用 JsonSchema 约束输出；课程 ID 必须回查 DB 校验存在性 |
| 4 | **RAG** | `langchain4j-easy-rag` + `AllMiniLmL6V2EmbeddingModel` 依赖已引入，**代码里一次没用**；且 AllMiniLmL6V2 是英文模型，中文场景效果差 | 换中文 embedding（bge-small-zh / text-embedding-3-small）；EmbeddingStore 直接用项目已有的 **Elasticsearch**（ES 8 原生支持 dense_vector + kNN）；用课程文档做 Ingestion → 检索增强问答 |
| 5 | **Tools / Function Calling** | 无 | 把"查询课程库/查询用户学习记录/查询订单"做成 `@Tool` 方法，让模型真正调业务系统，而不是靠正则猜 ID |
| 6 | **Streaming** | 全同步阻塞，`/ai/chat` 长响应会占满 Tomcat 线程 | `StreamingChatLanguageModel` + SSE（`Flux<String>` 或 `SseEmitter`）；配合已开启的虚拟线程 |
| 7 | **Prompt 管理** | 大段 prompt 硬编码在 Java 文本块里（`CourseAiServiceImpl` 4 处） | 外部化到 `prompts/*.txt` 或 DB/配置中心，支持热更新与 A/B |
| 8 | **韧性** | 每个方法 `catch(Exception)` 后返回"服务暂时不可用"，无重试、无超时差异化、无限流、无降级 | `spring-retry` / Resilience4j 限流熔断；区分可重试错误（429/5xx）与参数错误 |
| 9 | **可观测** | 只有 `log.info("响应时间: {}ms")` | Micrometer 埋点（token 消耗 / 首字延迟 / 错误率）；Boot 4 自带 OpenTelemetry starter |
| 10 | **成本与权限** | 无 | 按 userId 做 token 预算与配额；`logRequests(true).logResponses(true)` 会把完整请求响应（含用户隐私）打进日志 → 生产必须关闭 |
| 11 | **模型配置错误** | `application.yml` 与 `README.md`、`start-ai.sh` 都写 `ollama.model-name: gemini-3-flash-preview:cloud` —— **Ollama 官方模型库没有 gemini 系列模型**，这个模型名是虚构的 | 改为真实模型（如 `qwen2.5:7b`、`llama3.1:8b`）；`start-ai.sh` 里的 `ollama pull` 与 grep 判断同步修正 |

### 4.4 建议的 AI 目标架构

```
adapter 层   AiController (SSE 流式 + 同步)
                ↓
application  LearningAssistantAppService / CourseAdvisorAppService
                ↓
domain       提示词模板、对话策略、配额规则（纯 Java，不依赖框架）
                ↓
infra/ai     AiServices 装配 | Redis ChatMemoryStore | ES EmbeddingStore | @Tool 业务查询
```

关键原则：**领域层不 import `dev.langchain4j.*`**。把 LangChain4j 收敛到 `infra/ai`，上层只依赖自研接口 —— 这样模型供应商可替换，也让这块代码可测试。

---

## 5. 语言与编码现代化（P2）

`education-back` 实测统计：

| 指标 | 数量 | 说明 |
|---|---|---|
| `record` 定义 | **0** | 56 个类用 Lombok `@Data`，其中大量不可变 DTO/VO 是 record 的天然场景 |
| `var` 局部变量 | **0** | — |
| `sealed` / `permits` | **0** | — |
| `Optional` | **0** | 全局无使用 |
| 虚拟线程显式用法 | **0** | `spring.threads.virtual.enabled: true` 已开，但业务代码未受益 |
| 文本块 `"""` | 有（AI prompt） | 唯一已现代化的点 |
| switch 表达式 / pattern matching `instanceof` | **0** | — |
| `new Date()` / `SimpleDateFormat` | 6 处（2 文件） | `UserServiceImpl:68,69,134,140,167`、`RecruitController:50` |
| `java.util.Date` | 14 文件 | 应全面转 `java.time` |
| `java.util.stream` | 9 文件 | 相对 230 个类使用率偏低 |
| `@Autowired` 字段注入 | 36 类 | vs `@RequiredArgsConstructor` 构造器注入 18 类，比例 2:1 |
| `System.out.println` | 53 处（全项目） | 应全部转 SLF4J |
| `e.printStackTrace()` | 5 处 | 同上 |
| 参数校验注解 | **0 处** | 全局异常处理器已在处理 `MethodArgumentNotValidException`，却没有任何 `@Valid` |

**改造清单**：
1. 不可变 DTO / VO / 查询参数 → `record`（优先 `dto/`、`vo/`、`req/`）
2. `java.util.Date` → `LocalDateTime` / `Instant`；`new Date()` 用 MyBatis-Plus `MetaObjectHandler` 自动填充替代
3. 枚举化返回码：`ResultBody.code` 现为裸 `int`，已有 `ErrorCodeConstant` / `IErrorCode` 却未使用 → 接上
4. 字段注入 → 构造器注入（36 → 0），配合 `final` 字段
5. 补 `@Validated` + `jakarta.validation` 约束（`@NotBlank` / `@NotNull` / `@Valid`）
6. 5 个全局异常处理类合并为 1 个：
   - `handler/GlobalExceptionHandler.java`
   - `config/GExceptionHandler.java`
   - `advice/GlobalExceptionAdvice.java`
   - `advice/GlobalControllerAdvice.java`
   - `advice/GlobalResponseAdvice.java`
7. 巨型类拆分：`dto/BasActivity.java` **595 行**、`controller/ESController.java` **367 行**（含 15 处 `System.out`、9 处 fastjson）
8. 表达式化：工具类收敛（Guava 12 文件 + commons-lang3 7 文件 + Hutool 1 文件，三者功能高度重叠，建议保留 Hutool + Guava 之一）

**JDK 21 值得引入的特性**（这才是"学 JDK 21"的正确打开方式）：
- **虚拟线程**：`@Async` 任务、AI 流式调用、批量 IO —— 项目配置已开但没用上，配 `spring.threads.virtual.enabled=true` 后 Tomcat 请求线程已是虚拟线程，可在 AOP/日志里验证并做压测对比
- **Record Patterns + Pattern Matching for switch**：替换大量 `instanceof` + 强转（目前为 0，说明这类代码可能用了别的方式，值得排查）
- **SequencedCollection**（21 新增）：`getFirst()/getLast()/reversed()`
- **结构化并发（预览）**：AI 多路召回场景（同时查向量库 + 关键词库 + 业务库）可用 `StructuredTaskScope` 做并发编排与超时统一取消 —— 这是 JDK 21 + LangChain4j 的最佳结合点，强烈建议作为实践项目

---

## 6. 工程与质量基建（贯穿全程）

| 项 | 现状 | 建议 |
|---|---|---|
| 测试 | `src/test` **仅 1 个测试类**；根 pom `skipTests=true` | 移除 `skipTests`；先补 AI 层的集成测试（用 Ollama 本地模型或 WireMock 桩），再补核心 service |
| 配置 | `application-prod.yml`、`application-test.yml` 均为 **0 字节空文件**，但 `active: prod` | 补齐或删除；用 `spring.config.import` 拆配置 |
| 日志 | ECS 结构化格式已配（`logging.structured.format.console: ecs`），但 LOG_PATH 失效 | 按 §1.4 修复；MDC 已有 uri/uid/ip 埋点，值得保留 |
| 依赖管理 | 子 pom 大量重复声明（orika/commons-lang3/hutool/fastjson/… 在 4 个模块各写一遍） | 收敛到根 pom `dependencyManagement` + 一个 `common-dependencies` 聚合依赖 |
| 版本漂移 | `infra/pom.xml` 硬编码 `<source>23</source>`、`mybatis-plus-core` 单独写 version、`swagger-annotations` 在两处写了不同版本（2.2.22 / 2.2.27） | 统一由父 pom + BOM 管理，子模块不写 version |
| CI | GitHub Actions 全套 v1/v2，且 `mvn package -Dmaven.test.skip=true` | 升 v4；接 Spotless/ArchUnit（ArchUnit 可自动校验分层依赖方向，防止 `domain` 反向依赖 `infra`） |

---

## 7. 推荐执行路线

| 阶段 | 周期 | 内容 | 验收 |
|---|---|---|---|
| **P0 止血** | 1–2 天 | JDK 23→21 统一；密钥轮换 + 外置；`ddl-auto: none`；日志路径修复；actuator 收敛；删 springfox / IPLimitFilter / TestRedis / DemoConfig / 3 个 lombok jar | `mvn clean install` 在 JDK 21 通过；Docker 镜像可启动 |
| **P1 框架升级** | 1–2 周 | Boot 4.1.1 + Spring Cloud Oakwood；MyBatis-Plus 3.5.17（boot4 starter）；移除 ShardingSphere/Seata/pagehelper/fastjson1；LangChain4j 0.36.2 → 1.17.2 并迁移 API；Docker/CI 现代化 | 全模块编译通过；Swagger UI 与 `/ai/health` 正常 |
| **P2 语言现代化** | 1 周 | record 化 DTO/VO；`java.time` 全面替换；构造器注入；`@Validated` 补齐；5 个异常处理器合并；System.out 清零；虚拟线程验证 | 静态扫描（SonarQube / IDEA Inspect）关键项归零 |
| **P3 AI 工程化** | 2–3 周 | Redis ChatMemory；AiServices 声明式；结构化输出替代正则；ES 向量库 + 中文 embedding 落地 RAG；`@Tool` 业务函数调用；SSE 流式；限流降级可观测 | 有可演示的"课程知识库问答"端到端链路 + 单元测试 |
| **P4 架构收敛** | 持续 | `education-back` 四层拆分；领域下沉；ArchUnit 守门；测试覆盖率补到核心链路 | 分层依赖方向由 ArchUnit 自动化校验 |

**优先级判据**：P0/P1 是"能不能正确跑"和"技术栈是否过时"，直接决定这个项目的学习价值是否成立；P3 是你明确提到的 LangChain4j 充分实践，是本项目差异化收益最大的部分；P4 可以边做边收敛，不必一次到位。

---

## 8. 需要你决策的两点

1. **Spring Boot 目标版本**：直接 4.1.1（激进，一步到位，但要处理 Jackson 3 / Hibernate 7 与 Drools 的兼容验证），还是先 3.5.16 过渡（稳，但刚升就 EOL）？
2. **`domain` / `application` 骨架的去留**：按 §3.2 方案 A 做务实分层（把骨架用起来），还是方案 C（删掉，承认是分层单体）？

---

## 附：本次审计引用的权威来源

- Oracle Java SE Support Roadmap（JDK 21/23/25 支持期限）：https://www.oracle.com/java/technologies/java-se-support-roadmap.html
- endoflife.date / spring-boot（Spring Boot 各分支 EOL）：https://endoflife.date/spring-boot
- endoflife.date / oracle-jdk（JDK 各版本 EOL）：https://endoflife.date/oracle-jdk
- Maven Central — dev.langchain4j:langchain4j（当前 1.x 版本线）
- MyBatis-Plus 官网 / Maven Central（当前 3.5.17，`mybatis-plus-spring-boot3-starter` 坐标）
- Spring Boot 4.0 Release Notes（Java 17 基线、Jackson 3、模块化等 breaking changes）
