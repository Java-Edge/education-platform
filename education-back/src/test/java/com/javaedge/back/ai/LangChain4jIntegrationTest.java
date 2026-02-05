package com.javaedge.back.ai;

import com.javaedge.common.ai.model.AiChatRequest;
import com.javaedge.common.ai.model.AiChatResponse;
import com.javaedge.common.ai.model.CourseAiRecommendRequest;
import com.javaedge.common.ai.service.AiChatService;
import com.javaedge.common.ai.service.CourseAiService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * LangChain4j AI功能测试示例
 *
 * 运行前请确保：
 * 1. Ollama服务已启动（ollama serve）
 * 2. 已下载模型（ollama pull qwen2.5:7b）
 * 或者配置了有效的OpenAI API Key
 *
 * @author JavaEdge
 */
@Slf4j
@SpringBootTest
public class LangChain4jIntegrationTest {

    @Autowired
    private AiChatService aiChatService;

    @Autowired
    private CourseAiService courseAiService;

    /**
     * 测试简单聊天
     */
    @Test
    public void testSimpleChat() {
        log.info("=== 测试简单聊天 ===");

        String response = aiChatService.simpleChat("你好，请介绍一下Spring Boot框架");

        log.info("AI回复: {}", response);
        assert response != null && !response.isEmpty();
    }

    /**
     * 测试带系统提示的聊天
     */
    @Test
    public void testChatWithSystemPrompt() {
        log.info("=== 测试带系统提示的聊天 ===");

        String systemPrompt = "你是一位专业的Java技术导师，擅长用简洁的语言解释复杂概念。";
        String message = "什么是Spring Boot的自动配置？";

        String response = aiChatService.chatWithSystemPrompt(message, systemPrompt);

        log.info("AI回复: {}", response);
        assert response != null && !response.isEmpty();
    }

    /**
     * 测试完整聊天（带会话）
     */
    @Test
    public void testChatWithSession() {
        log.info("=== 测试会话聊天 ===");

        String sessionId = "test-session-" + System.currentTimeMillis();

        // 第一轮对话
        AiChatRequest request1 = AiChatRequest.builder()
                .message("我想学习微服务架构")
                .sessionId(sessionId)
                .userId(1)
                .build();

        AiChatResponse response1 = aiChatService.chat(request1);
        log.info("第一轮 - AI回复: {}", response1.getContent());
        log.info("使用Token: {}, 响应时间: {}ms",
            response1.getTokensUsed(), response1.getResponseTime());

        // 第二轮对话（保持上下文）
        AiChatRequest request2 = AiChatRequest.builder()
                .message("那我应该先学什么？")
                .sessionId(sessionId)
                .userId(1)
                .build();

        AiChatResponse response2 = aiChatService.chat(request2);
        log.info("第二轮 - AI回复: {}", response2.getContent());

        assert response1.getSuccess() && response2.getSuccess();
    }

    /**
     * 测试课程推荐
     */
    @Test
    public void testCourseRecommend() {
        log.info("=== 测试AI课程推荐 ===");

        CourseAiRecommendRequest request = CourseAiRecommendRequest.builder()
                .userId(1)
                .preference("喜欢实战项目，偏好视频课程")
                .skillLevel("中级")
                .learningGoal("成为全栈工程师")
                .limit(5)
                .build();

        List<Integer> courseIds = courseAiService.recommendCourses(request);

        log.info("推荐课程ID列表: {}", courseIds);
        assert courseIds != null && !courseIds.isEmpty();
    }

    /**
     * 测试生成课程描述
     */
    @Test
    public void testGenerateCourseDescription() {
        log.info("=== 测试生成课程描述 ===");

        String description = courseAiService.generateCourseDescription(
                "Spring Cloud微服务实战",
                "视频课程",
                "微服务, Spring Cloud, 分布式, Docker"
        );

        log.info("生成的课程描述:\n{}", description);
        assert description != null && description.length() > 100;
    }

    /**
     * 测试生成课程大纲
     */
    @Test
    public void testGenerateCourseOutline() {
        log.info("=== 测试生成课程大纲 ===");

        String outline = courseAiService.generateCourseOutline(
                "Python数据分析从入门到精通",
                "数据分析初学者",
                "40小时"
        );

        log.info("生成的课程大纲:\n{}", outline);
        assert outline != null && outline.length() > 200;
    }

    /**
     * 测试学习路径分析
     */
    @Test
    public void testAnalyzeLearningPath() {
        log.info("=== 测试学习路径分析 ===");

        List<String> completedCourses = Arrays.asList(
                "Java基础编程",
                "Spring框架入门",
                "MySQL数据库"
        );

        String analysis = courseAiService.analyzeLearningPath(
                1,
                completedCourses,
                "成为Java后端工程师"
        );

        log.info("学习路径分析:\n{}", analysis);
        assert analysis != null && analysis.length() > 100;
    }

    /**
     * 测试学习助手场景
     */
    @Test
    public void testLearningAssistant() {
        log.info("=== 测试学习助手场景 ===");

        String systemPrompt = "你是一位专业的在线教育学习助手，擅长解答学员在学习过程中遇到的各类问题。" +
                "请用简洁、专业、易懂的语言回答问题，必要时可以给出具体的学习建议和资源推荐。";

        String[] questions = {
                "如何快��掌握Docker容器技术？",
                "学习算法需要什么数学基础？",
                "前端框架Vue和React应该学哪个？"
        };

        for (String question : questions) {
            log.info("\n问题: {}", question);
            String answer = aiChatService.chatWithSystemPrompt(question, systemPrompt);
            log.info("回答: {}\n", answer);
        }
    }

    /**
     * 性能测试
     */
    @Test
    public void testPerformance() {
        log.info("=== AI性能测试 ===");

        int iterations = 3;
        long totalTime = 0;

        for (int i = 0; i < iterations; i++) {
            long startTime = System.currentTimeMillis();

            String response = aiChatService.simpleChat("请用一句话介绍Spring Boot");

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            totalTime += duration;

            log.info("第{}次调用 - 响应时间: {}ms, 响应长度: {}",
                i + 1, duration, response.length());
        }

        long avgTime = totalTime / iterations;
        log.info("平均响应时间: {}ms", avgTime);
    }
}
