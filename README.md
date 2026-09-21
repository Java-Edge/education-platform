# 在线教育平台

这是一个基于DDD架构的生产级在线教育应用，集成了AI智能功能。

## ✨ 核心特性

- 🎓 **在线课程管理** - 视频课程、专栏、学习路线
- 🤖 **AI智能助手** - AI对话和推荐能力（架构预留）
- 📊 **智能推荐** - AI驱动的个性化课程推荐
- 📝 **内容生成** - AI自动生成课程描述和大纲
- 🎯 **学习规划** - 智能学习路径分析
- 🔍 **全文搜索** - Elasticsearch支持
- 📋 **规则引擎** - Drools业务规则管理
- 🔐 **用户认证** - JWT + Spring Security
- 💾 **数据持久化** - MySQL + MyBatis Plus
- 🚀 **高性能缓存** - Redis + Caffeine

## 🚀 快速开始

### 1. 环境要求

- JDK 23+
- Spring Boot 3.4.1
- MySQL 8.0+
- Redis 6.0+
- Maven 3.8+

### 2. AI功能快速启动（可选）

```bash
# 安装Ollama（本地AI，无需API Key）
brew install ollama

# 启动Ollama
ollama serve

# 下载中文模型
ollama pull gemini-3-flash-preview:cloud
```

### 3. 启动项目

```bash
# 编译项目
mvn clean install -DskipTests

# 启动后端
cd education-back
mvn spring-boot:run
```

### 4. 访问应用

- API文档: http://localhost:8088/swagger-ui/index.html
- AI健康检查: http://localhost:8088/ai/health

## 🤖 AI功能演示

```bash
# 简单聊天
curl "http://localhost:8088/ai/chat/simple?message=介绍一下Spring Boot"

# 学习助手
curl "http://localhost:8088/ai/learning-assistant?question=如何学习Java编程？"

# 生成课程描述
curl -X POST "http://localhost:8088/ai/course/description?courseName=Spring Cloud微服务&courseType=视频课程"
```

## 📚 项目架构

```
education-platform/
├── common/          # 公共模块（工具类、AI服务等）
├── domain/          # 领域模型
├── infra/           # 基础设施层
├── application/     # 应用服务层
├── education-back/  # 后端启动模块
└── docs/           # 项目文档
```

## 📖 文档

- [AI集成文档](docs/AI_INTEGRATION.md) - AI能力接入指南
- [Drools规则引擎](docs/DROOLS_INTEGRATION.md) - 规则引擎使用指南

## 🛠️ 技术栈

### 后端技术
- Spring Boot 3.4.1
- Spring Security
- MyBatis Plus 3.5.3
- Drools 9.44.0 - 规则引擎
- Elasticsearch 8.x
- Redis
- MySQL 8.0

### AI能力（架构预留）
- **OpenAI** - GPT-4, GPT-4o-mini
- **Azure OpenAI** - 企业级AI服务
- **Ollama** - 本地大模型（qwen2.5、llama3等）

## 📊 数据库配置

在 `application.yml` 文件中配置数据库连接：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/education
    username: root
    password: 123456
```

## 🔑 API端点
- `GET /course/list` - 课程列表
- `GET /course/detail/{id}` - 课程详情
- `POST /course/create` - 创建课程

### 用户管理
- `POST /user/register` - 用户注册
- `POST /user/login` - 用户登录

## 💡 使用示例

### Java代码示例

```java
@Autowired
private AiChatService aiChatService;

@Autowired
private CourseAiService courseAiService;

// AI聊天
public String chatWithAI(String question) {
    return aiChatService.simpleChat(question);
}

// 生成课程描述
public String generateDescription(String courseName) {
    return courseAiService.generateCourseDescription(
        courseName, "视频课程", "Java,Spring"
    );
}

// 智能推荐
public List<Integer> recommend(Integer userId) {
    CourseAiRecommendRequest request = CourseAiRecommendRequest.builder()
        .userId(userId)
        .skillLevel("中级")
        .learningGoal("成为全栈工程师")
        .build();
    return courseAiService.recommendCourses(request);
}
```

## 🔧 配置选项

### 切换AI模型

## 🎯 业务场景

1. **在线课程管理** - 视频课程、专栏、学习路线
2. **学习规则引擎** - Drools 驱动的业务规则
3. **全文搜索** - Elasticsearch 课程检索
5. **知识问答** - 解答技术问题

## 📦 SQL脚本

联系公众号【JavaEdge】获取完整SQL脚本

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

## 📄 许可

本项目采用 MIT 许可证

## 📮 联系方式

- 公众号：JavaEdge
- 技术支持：通过公众号联系

---

**技术亮点**: DDD架构 + AI智能 + 规则引擎 + 全文搜索 + 微服务就绪

**最后更新**: 2026-01-19

