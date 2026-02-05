#!/bin/bash

# LangChain4j AI功能启动脚本
# 用于快速启动教育平台的AI功能

set -e

echo "================================"
echo "教育平台AI功能快速启动"
echo "================================"
echo ""

# 检查Ollama是否安装
if ! command -v ollama &> /dev/null; then
    echo "❌ Ollama未安装"
    echo ""
    echo "请先安装Ollama："
    echo "  macOS:   brew install ollama"
    echo "  Linux:   curl -fsSL https://ollama.com/install.sh | sh"
    echo "  Windows: https://ollama.com/download"
    echo ""
    exit 1
fi

echo "✅ Ollama已安装"

# 检查Ollama服务是否运行
if ! curl -s http://localhost:11434/api/tags > /dev/null 2>&1; then
    echo "⚠️  Ollama服务未运行"
    echo "正在启动Ollama服务..."
    ollama serve > /dev/null 2>&1 &
    sleep 3

    if curl -s http://localhost:11434/api/tags > /dev/null 2>&1; then
        echo "✅ Ollama服务已启动"
    else
        echo "❌ Ollama服务启动失败"
        echo "请手动运行: ollama serve"
        exit 1
    fi
else
    echo "✅ Ollama服务运行中"
fi

# 检查模型是否已下载
echo ""
echo "检查AI模型..."
if ollama list | grep -q "qwen2.5:7b"; then
    echo "✅ 模型 qwen2.5:7b 已安装"
else
    echo "⚠️  模型 qwen2.5:7b 未安装"
    echo "正在下载模型（约4.7GB，首次下载需要一些时间）..."
    ollama pull qwen2.5:7b
    echo "✅ 模型下载完成"
fi

echo ""
echo "================================"
echo "环境准备完成！"
echo "================================"
echo ""

# 编译项目
echo "正在编译项目..."
mvn clean install -DskipTests -q

if [ $? -eq 0 ]; then
    echo "✅ 项目编译成功"
else
    echo "❌ 项目编译失败"
    exit 1
fi

echo ""
echo "================================"
echo "启动教育平台..."
echo "================================"
echo ""

# 启动应用
cd education-back
mvn spring-boot:run &
APP_PID=$!

echo "应用启动中，PID: $APP_PID"
echo "等待应用就绪..."

# 等待应用启动
sleep 15

# 测试AI功能
echo ""
echo "================================"
echo "测试AI功能..."
echo "================================"
echo ""

if curl -s http://localhost:8088/ai/health > /dev/null; then
    echo "✅ AI服务健康检查通过"
    echo ""
    echo "测试简单聊天..."
    RESPONSE=$(curl -s "http://localhost:8088/ai/chat/simple?message=你好" | jq -r '.result' 2>/dev/null || echo "")

    if [ -n "$RESPONSE" ]; then
        echo "AI回复: $RESPONSE"
        echo ""
        echo "🎉 AI功能测试成功！"
    else
        echo "⚠️  AI回复为空，但服务运行正常"
    fi
else
    echo "⚠️  应用可能还在启动中..."
fi

echo ""
echo "================================"
echo "启动完成！"
echo "================================"
echo ""
echo "📱 访问地址："
echo "  - Swagger UI: http://localhost:8088/swagger-ui/index.html"
echo "  - AI健康检查: http://localhost:8088/ai/health"
echo ""
echo "🤖 快速测试命令："
echo "  curl 'http://localhost:8088/ai/chat/simple?message=介绍Spring Boot'"
echo "  curl 'http://localhost:8088/ai/learning-assistant?question=如何学习Java？'"
echo ""
echo "📚 文档："
echo "  - 快速入门: docs/LANGCHAIN4J_QUICKSTART.md"
echo "  - 完整文档: docs/LANGCHAIN4J_INTEGRATION.md"
echo ""
echo "⏹  停止应用: kill $APP_PID"
echo ""
