### 1. 安装Ollama（本地AI模型，需API Key）

```bash
# macOS
brew install ollama

# Linux
curl -fsSL https://ollama.com/install.sh | sh

# Windows - 下载安装包
# https://ollama.com/download
```

### 2. 启动Ollama并下载模型

```bash
# 启动Ollama服务
ollama serve

# 新开终端，下载中文模型（推荐）
ollama pull qwen2.5:7b
```

### 3. 启动项目

```bash
# 编译
mvn clean install -DskipTests

# 启动
cd education-back
mvn spring-boot:run
```

### 4. 测试AI功能

```bash
# 测试AI健康状态
curl http://localhost:8088/ai/health

# 简单对话
curl "http://localhost:8088/ai/chat/simple?message=你好，介绍一下Spring Boot"

# 学习助手
curl "http://localhost:8088/ai/learning-assistant?question=如何学习Java？"
```

## 主要功能

### 1. AI聊天助手
- **接口**: `GET /ai/chat/simple?message=xxx`
- **用途**: 智能客服、学习答疑

### 2. 智能课程推荐
- **接口**: `POST /ai/course/recommend`
- **用途**: 个性化课程推荐

### 3. 课程内容生成
- **接口**: `POST /ai/course/description`
- **用途**: 自动生成课程描述和大纲

### 4. 学习路径分析
- **接口**: `POST /ai/learning-path/analyze`
- **用途**: 规划学习路线

## 使用OpenAI（可选）

如果想使用OpenAI而不是本地模型，修改 `application.yml`:

```yaml
langchain4j:
  provider: openai
  openai:
    api-key: sk-your-api-key
    model-name: gpt-4o-mini
```

## 常用Ollama命令

```bash
# 查看已安装的模型
ollama list

# 运行模型（测试）
ollama run qwen2.5:7b

# 删除模型
ollama rm qwen2.5:7b

# 查看模型信息
ollama show qwen2.5:7b
```

## 推荐模型

| 模型 | 大小 | 特点 | 推荐场景 |
|------|------|------|----------|
| qwen2.5:7b | 4.7GB | 中文优秀，速度快 | **推荐** |
| llama3.1:8b | 4.7GB | 英文强，通用 | 英文场景 |
| mistral:7b | 4.1GB | 速度最快 | 低配机器 |
| qwen2.5:14b | 9.0GB | 更强能力 | 高配机器 |

## API示例

查看 Swagger UI: http://localhost:8088/swagger-ui/index.html

或参考完整文档: [docs/LANGCHAIN4J_INTEGRATION.md](./LANGCHAIN4J_INTEGRATION.md)

## 故障排查

### 问题1: Ollama连接失败
```bash
# 检查Ollama是否运行
ps aux | grep ollama

# 重启Ollama
pkill ollama
ollama serve
```

### 问题2: 模型响应慢
- 使用更小的模型（如mistral:7b）
- 减少max-tokens配置
- 检查机器性能

### 问题3: 内存不足
```bash
# 使用更小的模型
ollama pull qwen2.5:3b  # 只需2GB内存
```