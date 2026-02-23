# LangChain4j AI集成文档

## 概述

本项目已集成 **LangChain4j**，为教育平台提供强大的AI能力，包括：

- 🤖 **AI聊天助手** - 智能对话交互
- 📚 **智能课程推荐** - 基于用户画像的个性化推荐
- ✍️ **AI内容生成** - 自动生成课程描述、大纲
- 🎯 **学习路径分析** - 智能学习规划

## 快速开始

### 1. 环境准备

#### 选项A：使用Ollama本地模型（推荐用于开发）

```bash
# 1. 安装Ollama
# macOS
brew install ollama

# Linux
curl -fsSL https://ollama.com/install.sh | sh

# 2. 启动Ollama服务
ollama serve

# 3. 下载推荐模型（中文支持好）
ollama pull gemini-3-flash-preview:cloud

# 或者使用其他模型
# ollama pull llama3.1:8b
# ollama pull mistral:7b
```

#### 选项B：使用OpenAI（生产环境推荐）

修改 `application.yml`:

```yaml
langchain4j:
  provider: openai
  openai:
    api-key: sk-your-openai-api-key
    model-name: gpt-4o-mini
```

#### 选项C：使用Azure OpenAI

```yaml
langchain4j:
  provider: azure
  azure:
    api-key: your-azure-api-key
    endpoint: https://your-resource.openai.azure.com/
    deployment-name: gpt-4
```

### 2. 启动项目

```bash
# 编译项目
mvn clean install -DskipTests

# 启动后端服务
cd education-back
mvn spring-boot:run
```

### 3. 测试AI功能

访问 Swagger UI：http://localhost:8088/swagger-ui/index.html

或使用以下curl命令测试：

```bash
# 健康检查
curl http://localhost:8088/ai/health

# 简单聊天
curl "http://localhost:8088/ai/chat/simple?message=你好"

# 学习助手
curl "http://localhost:8088/ai/learning-assistant?question=如何学习Java编程？"
```

## API接口文档

### 1. AI聊天

#### 1.1 简单聊天

```http
GET /ai/chat/simple?message={message}
```

**示例：**
```bash
curl "http://localhost:8088/ai/chat/simple?message=介绍一下Spring Boot"
```

#### 1.2 完整聊天（支持会话上下文）

```http
POST /ai/chat
Content-Type: application/json

{
  "message": "如何学习微服务架构？",
  "sessionId": "user-123-session-1",
  "userId": 123,
  "systemPrompt": "你是一位资深的技术导师"
}
```

**响应：**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "content": "AI的回复内容...",
    "sessionId": "user-123-session-1",
    "tokensUsed": 150,
    "responseTime": 1234,
    "success": true
  }
}
```

#### 1.3 学习助手

```http
GET /ai/learning-assistant?question={question}
```

**示例：**
```bash
curl "http://localhost:8088/ai/learning-assistant?question=如何快速掌握Docker？"
```

### 2. AI课程推荐

#### 2.1 智能推荐课程

```http
POST /ai/course/recommend
Content-Type: application/json

{
  "userId": 123,
  "preference": "偏好实战项目，喜欢视频课程",
  "skillLevel": "中级",
  "learningGoal": "成为全栈工程师",
  "limit": 5
}
```

**响应：**
```json
{
  "code": 200,
  "message": "success",
  "data": [1, 2, 3, 4, 5]
}
```

### 3. AI内容生成

#### 3.1 生成课程描述

```http
POST /ai/course/description?courseName=Spring Cloud微服务&courseType=视频课程&keywords=微服务,分布式,云原生
```

**响应：**
```json
{
  "code": 200,
  "message": "success",
  "data": "本课程深入讲解Spring Cloud微服务架构..."
}
```

#### 3.2 生成课程大纲

```http
POST /ai/course/outline?courseName=Python数据分析&targetAudience=数据分析初学者&duration=40小时
```

**响应：**
```json
{
  "code": 200,
  "message": "success",
  "data": "# Python数据分析课程大纲\n\n## 第一章：Python基础..."
}
```

#### 3.3 学习路径分析

```http
POST /ai/learning-path/analyze?userId=123&learningGoal=成为AI工程师
Content-Type: application/json

[
  "Python基础编程",
  "数据结构与算法",
  "机器学习入门"
]
```

## 业务场景示例

### 场景1：智能客服

```java
@Autowired
private AiChatService aiChatService;

public String handleUserQuestion(String question) {
    String systemPrompt = "你是教育平台的智能客服，帮助用户解决使用问题。";
    return aiChatService.chatWithSystemPrompt(question, systemPrompt);
}
```

### 场景2：课程内容生成

```java
@Autowired
private CourseAiService courseAiService;

public String createCourseDescription(Course course) {
    return courseAiService.generateCourseDescription(
        course.getName(),
        course.getType(),
        course.getKeywords()
    );
}
```

### 场景3：个性化学习建议

```java
@Autowired
private CourseAiService courseAiService;

public String getStudyAdvice(Integer userId) {
    List<String> completed = userCourseService.getCompletedCourses(userId);
    String goal = userProfileService.getLearningGoal(userId);
    
    return courseAiService.analyzeLearningPath(userId, completed, goal);
}
```

## 配置说明

### 整配置选项

```yaml
langchain4j:
  # 提供商选择
  provider: ollama  # openai | azure | ollama
  
  # OpenAI配置
  openai:
    api-key: sk-xxx
    model-name: gpt-4o-mini  # gpt-4o-mini | gpt-4 | gpt-3.5-turbo
    base-url: https://api.openai.com/v1
  
  # Azure OpenAI配置
  azure:
    api-key: xxx
    endpoint: https://xxx.openai.azure.com/
    deployment-name: gpt-4
  
  # Ollama本地模型配置
  ollama:
    base-url: http://localhost:11434
    model-name: gemini-3-flash-preview:cloud  # gemini-3-flash-preview:cloud | llama3.1:8b | mistral:7b
  
  # 通用参数
  timeout: 60           # 超时时间（秒）
  temperature: 0.7      # 随机性控制 0-1
  max-tokens: 2000      # 最大生成token数
  
  # 嵌入模型
  embedding:
    enabled: true
```

### 环境变量配置

也可以通过环境变量配置：

```bash
export LANGCHAIN4J_PROVIDER=openai
export OPENAI_API_KEY=sk-your-key
export LANGCHAIN4J_OPENAI_MODEL_NAME=gpt-4o-mini
```

## 性能优化建议

### 1. 响应缓存

对于相同的问题，可以缓存AI响应：

```java
@Cacheable(value = "ai-responses", key = "#message")
public String simpleChat(String message) {
    return chatLanguageModel.generate(message);
}
```

### 2. 异步处理

对于非实时场景，使用异步调用：

```java
@Async
public CompletableFuture<String> asyncGenerateDescription(String courseName) {
    String result = courseAiService.generateCourseDescription(courseName, "", "");
    return CompletableFuture.completedFuture(result);
}
```

### 3. 会话管理

生产环境建议使用Redis存储会话：

```java
@Service
public class RedisSessionStore {
    @Autowired
    private RedisTemplate<String, List<ChatMessage>> redisTemplate;
    
    public void saveSession(String sessionId, List<ChatMessage> messages) {
        redisTemplate.opsForValue().set(
            "ai:session:" + sessionId, 
            messages, 
            30, 
            TimeUnit.MINUTES
        );
    }
}
```

## 常见问题

### Q1: Ollama连接失败？

**A:** 确保Ollama服务已启动：
```bash
ollama serve
# 测试连接
curl http://localhost:11434/api/tags
```

### Q2: OpenAI API调用失败？

**A:** 检查：
1. API Key是否正确
2. 账户是否有余额
3. 网络连接是否正常
4. base-url是否配置正确

### Q3: 响应速度慢？

**A:** 
1. 使用本地模型（Ollama）可以加快速度
2. 减小max-tokens参数
3. 使用更快的模型（如gpt-4o-mini而不是gpt-4）
4. 启用响应缓存

### Q4: 如何切换不同的AI模型？

**A:** 修改application.yml中的provider配置：
```yaml
langchain4j:
  provider: openai  # 改为 ollama 或 azure
```

## 扩展开发

### 自定义AI服务

```java
@Service
public class CustomAiService {
    @Autowired
    private ChatLanguageModel chatModel;
    
    public String customFunction(String input) {
        String prompt = "根据业务需求定制的提示词: " + input;
        return chatModel.generate(prompt);
    }
}
```

### 集成RAG

```java
@Service
public class RagService {
    @Autowired
    private EmbeddingModel embeddingModel;
    
    @Autowired
    private ChatLanguageModel chatModel;
    
    // 实现基于课程库的RAG检索
}
```

## 最佳实践

1. **提示词工程**：编写清晰、具体的提示词
2. **错误处理**：始终处理API调用异常
3. **成本控制**：合理设置max-tokens，避免过度调用
4. **安全性**：不要将API Key硬编码，使用环境变量
5. **监控**：记录AI调用次数、响应时间、成本

## 更多资源

- [LangChain4j官方文档](https://docs.langchain4j.dev/)
- [Ollama模型库](https://ollama.com/library)
- [OpenAI API文档](https://platform.openai.com/docs)

---
