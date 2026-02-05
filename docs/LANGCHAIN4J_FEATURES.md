# LangChain4j AI功能演示

## 🎯 功能概览

本教育平台已集成LangChain4j，提供8大AI核心功能：

1. 💬 **AI聊天助手** - 智能对话交互
2. 🎓 **学习助手** - 专业学习辅导
3. 📚 **课程推荐** - 个性化智能推荐
4. ✍️ **课程描述生成** - 自动文案创作
5. 📋 **课程大纲生成** - 智能课程设计
6. 🗺️ **学习路径分析** - 个性化学习规划
7. 🔍 **健康检查** - AI服务状态监控
8. 💾 **会话管理** - 上下文感知对话

---

## 1️⃣ AI聊天助手

### 功能说明
与AI进行自然语言对话，支持任意话题交流。

### API接口
```http
GET /ai/chat/simple?message={question}
```

### 使用示例

**请求:**
```bash
curl "http://localhost:8088/ai/chat/simple?message=介绍一下Spring Boot框架"
```

**响应:**
```json
{
  "code": 200,
  "message": "success",
  "result": "Spring Boot是一个基于Spring框架的开源Java框架，它简化了Spring应用的初始搭建和开发过程。主要特点包括：\n1. 自动配置：根据项目依赖自动配置Spring应用\n2. 独立运行：内嵌Web服务器，可以直接运行\n3. 起步依赖：简化Maven/Gradle配置\n4. 生产就绪：提供监控、健康检查等功能\n5. 无代码生成：不需要XML配置..."
}
```

### Java代码示例
```java
@Autowired
private AiChatService aiChatService;

public String askQuestion(String question) {
    return aiChatService.simpleChat(question);
}
```

---

## 2️⃣ 学习助手

### 功能说明
专门针对学习场景优化的AI助手，提供学习建议和资源推荐。

### API接口
```http
GET /ai/learning-assistant?question={question}
```

### 使用示例

**请求:**
```bash
curl "http://localhost:8088/ai/learning-assistant?question=如何快速掌握Docker容器技术？"
```

**响应:**
```json
{
  "code": 200,
  "message": "success",
  "result": "快速掌握Docker的学习路径：\n\n1. 基础概念（1-2天）\n- 了解容器vs虚拟机的区别\n- 学习Docker架构和核心概念\n- 安装Docker Desktop\n\n2. 基本操作（3-5天）\n- 镜像管理：pull、build、push\n- 容器操作：run、start、stop\n- 常用命令实践\n\n3. Dockerfile编写（5-7天）\n- 理解Dockerfile指令\n- 编写优化的镜像\n- 多阶段构建\n\n4. 实战项目（1-2周）\n- 容器化Spring Boot应用\n- Docker Compose多容器编排\n- 容器网络和数据卷\n\n推荐资源：\n- 官方文档：docs.docker.com\n- 实践课程：Docker从入门到实战"
}
```

**应用场景:**
- 学员咨询学习方法
- 技术难点解答
- 学习资源推荐
- 职业发展建议

---

## 3️⃣ 智能课程推荐

### 功能说明
基于用户画像（技能水平、学习目标、偏好）智能推荐最适合的课程。

### API接口
```http
POST /ai/course/recommend
Content-Type: application/json
```

### 使用示例

**请求体:**
```json
{
  "userId": 123,
  "preference": "喜欢实战项目，偏好视频课程",
  "skillLevel": "中级",
  "learningGoal": "成为全栈工程师",
  "limit": 5
}
```

**请求:**
```bash
curl -X POST http://localhost:8088/ai/course/recommend \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 123,
    "preference": "喜欢实战项目，偏好视频课程",
    "skillLevel": "中级",
    "learningGoal": "成为全栈工程师",
    "limit": 5
  }'
```

**响应:**
```json
{
  "code": 200,
  "message": "success",
  "result": [1, 5, 8, 12, 15]
}
```

**Java代码:**
```java
CourseAiRecommendRequest request = CourseAiRecommendRequest.builder()
    .userId(123)
    .preference("喜欢实战项目")
    .skillLevel("中级")
    .learningGoal("成为全栈工程师")
    .limit(5)
    .build();

List<Integer> courseIds = courseAiService.recommendCourses(request);
```

---

## 4️⃣ 课程描述生成

### 功能说明
AI自动生成专业、吸引人的课程描述文案。

### API接口
```http
POST /ai/course/description?courseName={name}&courseType={type}&keywords={keywords}
```

### 使用示例

**请求:**
```bash
curl -X POST "http://localhost:8088/ai/course/description?courseName=Spring Cloud微服务实战&courseType=视频课程&keywords=微服务,Spring Cloud,分布式,Docker"
```

**响应:**
```json
{
  "code": 200,
  "message": "success",
  "result": "本课程深入讲解Spring Cloud微服务架构的核心技术与实战应用。通过系统化学习，你将掌握服务注册与发现、配置中心、负载均衡、断路器、API网关等关键组件的使用。\n\n核心内容包括：\n1. 微服务架构设计原则与最佳实践\n2. Spring Cloud全家桶组件深度剖析\n3. 分布式系统的常见问题与解决方案\n4. Docker容器化部署实践\n5. 真实企业级项目实战演练\n\n适合人群：\n- 具有Spring Boot基础的Java开发者\n- 希望转型微服务架构的后端工程师\n- 追求技术深度的系统架构师\n\n学完本课程，你将能够独立设计和搭建企业级微服务系统，掌握分布式架构的核心能力，为职业发展打下坚实基础。"
}
```

**应用场景:**
- 新课程上线时自动生成描述
- 优化现有课程文案
- 多语言课程描述生成
- A/B测试不同描述效果

---

## 5️⃣ 课程大纲生成

### 功能说明
根据课程信息自动生成结构化的教学大纲。

### API接口
```http
POST /ai/course/outline?courseName={name}&targetAudience={audience}&duration={duration}
```

### 使用示例

**请求:**
```bash
curl -X POST "http://localhost:8088/ai/course/outline?courseName=Python数据分析从入门到精通&targetAudience=数据分析初学者&duration=40小时"
```

**响应:**
```json
{
  "code": 200,
  "message": "success",
  "result": "# Python数据分析从入门到精通\n\n## 课程目标\n1. 掌握Python编程基础和数据分析核心库\n2. 能够独立完成数据清洗、分析和可视化\n3. 理解数据分析的完整工作流程\n4. 具备解决实际业务问题的能力\n\n## 第一章：Python基础（8小时）\n### 1.1 Python环境搭建\n- Anaconda安装与配置\n- Jupyter Notebook使用\n- 虚拟环境管理\n\n### 1.2 Python语法基础\n- 变量、数据类型\n- 控制流程\n- 函数定义与使用\n\n### 1.3 Python数据结构\n- 列表、元组、字典\n- 集合操作\n- 列表推导式\n\n## 第二章：NumPy数值计算（6小时）\n### 2.1 NumPy数组\n- 数组创建与属性\n- 数组索引与切片\n- 数组运算\n\n### 2.2 数组操作\n- 形状变换\n- 数组拼接与分割\n- 广播机制\n\n## 第三章：Pandas数据处理（10小时）\n### 3.1 Series和DataFrame\n- 数据结构详解\n- 数据读取与写入\n- 索引操作\n\n### 3.2 数据清洗\n- 缺失值处理\n- 重复值处理\n- 异常值检测\n\n### 3.3 数据转换\n- 数据类型转换\n- 字符串处理\n- 时间序列处理\n\n### 3.4 数据分组与聚合\n- GroupBy操作\n- 聚合函数\n- 数据透视表\n\n## 第四章：数据可视化（8小时）\n### 4.1 Matplotlib基础\n- 图表类型\n- 图表样式定制\n- 子图布局\n\n### 4.2 Seaborn高级可视化\n- 统计图表\n- 主题设置\n- 颜色配置\n\n### 4.3 交互式可视化\n- Plotly入门\n- 动态图表\n- 仪表板制作\n\n## 第五章：实战项目（8小时）\n### 5.1 电商数据分析\n- 用户行为分析\n- 销售趋势分析\n- RFM模型应用\n\n### 5.2 金融数据分析\n- 股票数据获取\n- 技术指标计算\n- 投资组合分析\n\n### 5.3 综合项目\n- 完整数据分析流程\n- 分析报告撰写\n- 结果可视化呈现\n\n## 推荐学习资源\n- 官方文档：pandas.pydata.org\n- Kaggle数据集\n- 开源分析项目"
}
```

---

## 6️⃣ 学习路径分析

### 功能说明
基于学员的学习历史，AI智能规划后续学习路径。

### API接口
```http
POST /ai/learning-path/analyze?userId={userId}&learningGoal={goal}
Content-Type: application/json
```

### 使用示例

**请求体:** (已完成课程列表)
```json
[
  "Java基础编程",
  "Spring框架入门",
  "MySQL数据库"
]
```

**请求:**
```bash
curl -X POST "http://localhost:8088/ai/learning-path/analyze?userId=123&learningGoal=成为Java后端工程师" \
  -H "Content-Type: application/json" \
  -d '["Java基础编程", "Spring框架入门", "MySQL数据库"]'
```

**响应:**
```json
{
  "code": 200,
  "message": "success",
  "result": "# 学习进度评估\n\n当前进度：初级→中级过渡阶段（35%）\n\n已掌握技能：\n- ✅ Java核心编程能力\n- ✅ Spring基础框架\n- ✅ 关系型数据库基础\n\n## 技能图谱分析\n\n### 已完成模块\n1. 编程语言基础 ███████░░░ 70%\n2. 框架技术 ████░░░░░░ 40%\n3. 数据库技术 ████░░░░░░ 40%\n4. 工具链 ██░░░░░░░░ 20%\n\n### 待提升模块\n- Web开发\n- 微服务架构\n- 中间件技术\n- 项目实战经验\n\n## 后续推荐课程\n\n### 第一优先级（建议1-2个月）\n1. **Spring Boot实战** ⭐⭐⭐⭐⭐\n   - 学习时长：30小时\n   - 重要性：核心必修\n   - 原因：是当前Java后端的主流框架\n\n2. **Redis缓存技术** ⭐⭐⭐⭐\n   - 学习时长：15小时\n   - 重要性：高频使用\n   - 原因：提升系统性能的关键技术\n\n### 第二优先级（建议2-3个月后）\n3. **Spring Cloud微服务** ⭐⭐⭐⭐⭐\n   - 学习时长：40小时\n   - 需要前置：Spring Boot\n   - 原因：企业级架构必备\n\n4. **消息队列（RabbitMQ/Kafka）** ⭐⭐⭐⭐\n   - 学习时长：20小时\n   - 原因：异步处理和削峰填谷\n\n### 第三优先级（建议后续深化）\n5. **分布式系统设计** ⭐⭐⭐⭐\n6. **Docker容器化** ⭐⭐⭐\n7. **系统性能优化** ⭐⭐⭐\n\n## 预计学习时间\n\n- 达到中级水平：3-4个月\n- 达到高级水平：8-12个月\n- 具备架构能力：18-24个月\n\n## 学习建议\n\n1. **循序渐进**：先掌握Spring Boot，再学习微服务\n2. **理论结合实践**：每学一个技术都要做项目实战\n3. **关注源码**：阅读Spring Boot源码理解原理\n4. **持续输出**：写技术博客巩固知识\n5. **参与开源**：贡献开源项目提升能力\n\n## 职业发展路径\n\n完成推荐学习路径后，你将具备：\n- 独立开发企业级Web应用\n- 微服务架构设计能力\n- 高并发系统优化经验\n- 3-5年工作经验等效技能\n\n预期薪资范围：15K-25K（一线城市）"
}
```

---

## 7️⃣ 完整会话对话

### 功能说明
支持上下文感知的多轮对话，AI能记住对话历史。

### API接口
```http
POST /ai/chat
Content-Type: application/json
```

### 使用示例

**第一轮对话:**
```json
{
  "message": "我想学习微服务架构",
  "sessionId": "user-123-session-1",
  "userId": 123
}
```

**响应:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "content": "很好的选择！微服务架构是当前主流的分布式系统架构模式。在开始学习之前，你目前的技术水平如何？是否已经掌握了Spring Boot？",
    "sessionId": "user-123-session-1",
    "tokensUsed": 85,
    "responseTime": 1234,
    "success": true
  }
}
```

**第二轮对话:**（AI记住了上下文）
```json
{
  "message": "我已经掌握了Spring Boot基础",
  "sessionId": "user-123-session-1",
  "userId": 123
}
```

**响应:**
```json
{
  "data": {
    "content": "太好了！既然你已经掌握了Spring Boot，那么学习Spring Cloud微服务会容易很多。建议你按以下顺序学习：\n1. 服务注册与发现（Eureka/Nacos）\n2. 配置中心（Config）\n3. 负载均衡（Ribbon）\n4. 服务调用（Feign）\n5. 熔断器（Hystrix/Sentinel）\n6. API网关（Gateway）\n你想从哪个模块开始？",
    "sessionId": "user-123-session-1",
    "success": true
  }
}
```

---

## 8️⃣ AI服务健康检查

### 功能说明
检查AI服务是否正常运行。

### API接口
```http
GET /ai/health
```

### 使用示例

**请求:**
```bash
curl http://localhost:8088/ai/health
```

**响应（正常）:**
```json
{
  "code": 200,
  "message": "success",
  "result": {
    "status": "UP",
    "message": "AI service is running",
    "testResponse": "Hello! I'm ready to assist you..."
  }
}
```

**响应（异常）:**
```json
{
  "code": -1,
  "message": "AI服务不可用",
  "result": {
    "status": "DOWN",
    "message": "AI service is unavailable",
    "error": "Connection refused"
  }
}
```

---

## 🎮 完整使用流程演示

### 场景：新学员注册后的学习规划

```bash
# 1. 学员提问：我是零基础，想成为Java工程师
curl "http://localhost:8088/ai/learning-assistant?question=我是零基础，想成为Java工程师，应该如何开始？"

# 2. 根据AI建议，推荐入门课程
curl -X POST http://localhost:8088/ai/course/recommend \
  -H "Content-Type: application/json" \
  -d '{"userId": 123, "skillLevel": "初级", "learningGoal": "成为Java工程师", "limit": 3}'

# 3. 为推荐的课程生成描述
curl -X POST "http://localhost:8088/ai/course/description?courseName=Java零基础入门&courseType=视频课程&keywords=Java,编程基础"

# 4. 学习3个月后，分析学习路径
curl -X POST "http://localhost:8088/ai/learning-path/analyze?userId=123&learningGoal=成为Java工程师" \
  -H "Content-Type: application/json" \
  -d '["Java基础", "面向对象编程", "集合框架"]'
```

---

## 📊 功能对比

| 功能 | 简单模式 | 专业模式 | 会话模式 |
|------|---------|---------|---------|
| 简单聊天 | ✅ | ❌ | ❌ |
| 学习助手 | ❌ | ✅ | ❌ |
| 课程推荐 | ❌ | ✅ | ❌ |
| 完整对话 | ❌ | ❌ | ✅ |
| 上下文记忆 | ❌ | ❌ | ✅ |
| 响应速度 | 快 | 中 | 慢 |
| 质量 | 一般 | 高 | 最高 |

---

## 🎯 业务价值

1. **提升用户体验** - 24/7智能客服，即时响应
2. **个性化服务** - 千人千面的课程推荐
3. **降低成本** - 减少人工客服和课程设计成本
4. **提高转化** - AI生成的专业文案提升购买率
5. **数据驱动** - 学习路径分析帮助学员规划
6. **扩展性强** - 轻松支持多语言、多领域

---

**更新时间**: 2026-01-19  
**文档版本**: 1.0  
**作者**: JavaEdge
