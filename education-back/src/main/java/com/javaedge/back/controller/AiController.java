package com.javaedge.back.controller;

import com.javaedge.common.ai.model.AiChatRequest;
import com.javaedge.common.ai.model.AiChatResponse;
import com.javaedge.common.ai.model.CourseAiRecommendRequest;
import com.javaedge.common.ai.service.AiChatService;
import com.javaedge.common.ai.service.CourseAiService;
import com.javaedge.common.resp.ResultBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AI功能控制器
 * 提供AI聊天、课程推荐、内容生成等功能
 *
 * @author JavaEdge
 */
@Slf4j
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
@Tag(name = "AI功能", description = "AI聊天、智能推荐、内容生成")
public class AiController {

    private final AiChatService aiChatService;
    private final CourseAiService courseAiService;

    /**
     * AI聊天接口
     * POST /ai/chat
     */
    @PostMapping("/chat")
    @Operation(summary = "AI聊天", description = "与AI进行对话交互")
    public ResultBody chat(@RequestBody AiChatRequest request) {
        log.info("AI聊天请求 - userId: {}, message: {}", request.getUserId(), request.getMessage());

        try {
            AiChatResponse response = aiChatService.chat(request);
            return ResultBody.success(response);
        } catch (Exception e) {
            log.error("AI聊天失败", e);
            return ResultBody.error("AI服务暂时不可用，请稍后再试");
        }
    }

    /**
     * 简单聊天（快捷接口）
     * GET /ai/chat/simple?message=xxx
     */
    @GetMapping("/chat/simple")
    @Operation(summary = "简单聊天", description = "快速发送消息给AI")
    public ResultBody simpleChat(@RequestParam String message) {
        log.info("简单聊天请求 - message: {}", message);

        try {
            String response = aiChatService.simpleChat(message);
            return ResultBody.success(response);
        } catch (Exception e) {
            log.error("简单聊天失败", e);
            return ResultBody.error("AI服务暂时不可用，请稍后再试");
        }
    }

    /**
     * 学习助手 - 课程相关问题解答
     * GET /ai/learning-assistant?question=xxx
     */
    @GetMapping("/learning-assistant")
    @Operation(summary = "学习助手", description = "解答学习相关问题")
    public ResultBody learningAssistant(@RequestParam String question) {
        log.info("学习助手请求 - question: {}", question);

        try {
            String systemPrompt = "你是一位专业的在线教育学习助手，擅长解答学员在学习过程中遇到的各类问题。" +
                "请用简洁、专业、易懂的语言回答问题，必要时可以给出具体的学习建议和资源推荐。";

            String response = aiChatService.chatWithSystemPrompt(question, systemPrompt);
            return ResultBody.success(response);
        } catch (Exception e) {
            log.error("学习助手失败", e);
            return ResultBody.error("学习助手暂时不可用，请稍后再试");
        }
    }

    /**
     * AI课程推荐
     * POST /ai/course/recommend
     */
    @PostMapping("/course/recommend")
    @Operation(summary = "AI课程推荐", description = "基于用户画像智能推荐课程")
    public ResultBody recommendCourses(@RequestBody CourseAiRecommendRequest request) {
        log.info("AI课程推荐请求 - userId: {}, learningGoal: {}",
            request.getUserId(), request.getLearningGoal());

        try {
            List<Integer> courseIds = courseAiService.recommendCourses(request);
            return ResultBody.success(courseIds);
        } catch (Exception e) {
            log.error("AI课程推荐失败", e);
            return ResultBody.error("推荐服务暂时不可用，请稍后再试");
        }
    }

    /**
     * 生成课程描述
     * POST /ai/course/description
     */
    @PostMapping("/course/description")
    @Operation(summary = "生成课程描述", description = "AI生成课程描述文案")
    public ResultBody generateDescription(
            @RequestParam String courseName,
            @RequestParam String courseType,
            @RequestParam(required = false) String keywords) {

        log.info("生成课程描述 - courseName: {}, courseType: {}", courseName, courseType);

        try {
            String description = courseAiService.generateCourseDescription(
                courseName, courseType, keywords != null ? keywords : "");
            return ResultBody.success(description);
        } catch (Exception e) {
            log.error("生成课程描述失败", e);
            return ResultBody.error("描述生成失败，请稍后再试");
        }
    }

    /**
     * 生成课程大纲
     * POST /ai/course/outline
     */
    @PostMapping("/course/outline")
    @Operation(summary = "生成课程大纲", description = "AI生成课程教学大纲")
    public ResultBody generateOutline(
            @RequestParam String courseName,
            @RequestParam String targetAudience,
            @RequestParam String duration) {

        log.info("生成课程大纲 - courseName: {}, targetAudience: {}, duration: {}",
            courseName, targetAudience, duration);

        try {
            String outline = courseAiService.generateCourseOutline(courseName, targetAudience, duration);
            return ResultBody.success(outline);
        } catch (Exception e) {
            log.error("生成课程大纲失败", e);
            return ResultBody.error("大纲生成失败，请稍后再试");
        }
    }

    /**
     * 学习路径分析
     * POST /ai/learning-path/analyze
     */
    @PostMapping("/learning-path/analyze")
    @Operation(summary = "学习路径分析", description = "分析学习进度并推荐后续课程")
    public ResultBody analyzeLearningPath(
            @RequestParam Integer userId,
            @RequestBody List<String> completedCourses,
            @RequestParam String learningGoal) {

        log.info("学习路径分析 - userId: {}, completedCourses: {}, learningGoal: {}",
            userId, completedCourses.size(), learningGoal);

        try {
            String analysis = courseAiService.analyzeLearningPath(userId, completedCourses, learningGoal);
            return ResultBody.success(analysis);
        } catch (Exception e) {
            log.error("学习路径分析失败", e);
            return ResultBody.error("分析失败，请稍后再试");
        }
    }

    /**
     * AI健康检查
     * GET /ai/health
     */
    @GetMapping("/health")
    @Operation(summary = "AI服务健康检查", description = "检查AI服务是否正常")
    public ResultBody healthCheck() {
        try {
            String response = aiChatService.simpleChat("Hello");

            Map<String, Object> health = new HashMap<>();
            health.put("status", "UP");
            health.put("message", "AI service is running");
            health.put("testResponse", response);

            return ResultBody.success(health);
        } catch (Exception e) {
            log.error("AI服务健康检查失败", e);

            Map<String, Object> health = new HashMap<>();
            health.put("status", "DOWN");
            health.put("message", "AI service is unavailable");
            health.put("error", e.getMessage());

            ResultBody body = ResultBody.error("AI服务不可用");
            body.setResult(health);
            return body;
        }
    }
}
