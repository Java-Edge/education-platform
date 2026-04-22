package com.javaedge.common.ai.service.impl;

import com.javaedge.common.ai.model.CourseAiRecommendRequest;
import com.javaedge.common.ai.service.CourseAiService;
import dev.langchain4j.model.chat.ChatLanguageModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 课程AI服务实现
 *
 * @author JavaEdge
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CourseAiServiceImpl implements CourseAiService {

    private final ChatLanguageModel chatLanguageModel;
    private static final Pattern COURSE_ID_PATTERN = Pattern.compile("\\d+");

    @Override
    public List<Integer> recommendCourses(CourseAiRecommendRequest request) {
        log.info("AI课程推荐 - userId: {}, preference: {}, skillLevel: {}",
            request.getUserId(), request.getPreference(), request.getSkillLevel());

        try {
            String prompt = buildRecommendPrompt(request);
            String response = chatLanguageModel.generate(prompt);

            log.debug("AI推荐结果: {}", response);

            // 从AI响应中提取课程ID（这里简化处理，实际应该与课程库匹配）
            return extractCourseIds(response);

        } catch (Exception e) {
            log.error("AI课程推荐失败", e);
            return List.of();
        }
    }

    @Override
    public String generateCourseDescription(String courseName, String courseType, String keywords) {
        log.info("生成课程描述 - courseName: {}, courseType: {}, keywords: {}",
            courseName, courseType, keywords);

        try {
            String prompt = """
                作为一位教育专家，请为以下课程撰写一段吸引人的描述（200-300字）：
                课程名称：%s
                课程类型：%s
                关键词：%s

                要求：
                1. 突出课程的核心价值和学习收获
                2. 说明适合的目标学员
                3. 语言专业且易懂
                4. 具有激励性
                """.formatted(courseName, courseType, keywords);

            String description = chatLanguageModel.generate(prompt);
            log.debug("生成的课程描述: {}", description);
            return description;

        } catch (Exception e) {
            log.error("生成课程描述失败", e);
            return "课程描述生成失败，请稍后再试。";
        }
    }

    @Override
    public String generateCourseOutline(String courseName, String targetAudience, String duration) {
        log.info("生成课程大纲 - courseName: {}, targetAudience: {}, duration: {}",
            courseName, targetAudience, duration);

        try {
            String prompt = """
                作为一位资深课程设计师，请为以下课程设计一个详细的教学大纲：
                课程名称：%s
                目标学员：%s
                课程时长：%s

                请包含以下内容：
                1. 课程目标（3-5条）
                2. 章节划分（5-8个章节，每个章节包含2-4个小节）
                3. 每个章节的学习要点
                4. 建议的实践项目

                请以结构化的Markdown格式输出。
                """.formatted(courseName, targetAudience, duration);

            String outline = chatLanguageModel.generate(prompt);
            log.debug("生成的课程大纲: {}", outline);
            return outline;

        } catch (Exception e) {
            log.error("生成课程大纲失败", e);
            return "课程大纲生成失败，请稍后再试。";
        }
    }

    @Override
    public String analyzeLearningPath(Integer userId, List<String> completedCourses, String learningGoal) {
        log.info("分析学习路径 - userId: {}, completedCourses: {}, learningGoal: {}",
            userId, completedCourses, learningGoal);

        try {
            String prompt = """
                作为一位学习顾问，请为学员分析学习路径：
                已完成课程：%s
                学习目标：%s

                请提供：
                1. 学习进度评估
                2. 技能图谱分析
                3. 后续推荐课程（按优先级排序）
                4. 预计学习时间
                5. 学习建议
                """.formatted(String.join(", ", completedCourses), learningGoal);

            String analysis = chatLanguageModel.generate(prompt);
            log.debug("学习路径分析: {}", analysis);
            return analysis;

        } catch (Exception e) {
            log.error("学习路径分析失败", e);
            return "学习路径分析失败，请稍后再试。";
        }
    }

    /**
     * 构建课程推荐提示词
     */
    private String buildRecommendPrompt(CourseAiRecommendRequest request) {
        String preference = Objects.requireNonNullElse(request.getPreference(), "无特殊偏好");
        String skillLevel = Objects.requireNonNullElse(request.getSkillLevel(), "初级");
        String learningGoal = Objects.requireNonNullElse(request.getLearningGoal(), "技能提升");
        return """
            作为一位智能教育顾问，请根据以下信息推荐适合的课程：
            学习偏好：%s
            技能水平：%s
            学习目标：%s
            推荐数量：%d

            请按优先级推荐课程，并说明推荐理由。重点考虑：
            1. 与学习目标的匹配度
            2. 与当前技能水平的适配性
            3. 学习路径的连贯性
            4. 实用性和就业价值
            """.formatted(preference, skillLevel, learningGoal, request.getLimit());
    }

    /**
     * 从AI响应中提取课程ID（简化实现）
     */
    private List<Integer> extractCourseIds(String response) {
        List<Integer> courseIds = new ArrayList<>();

        // 尝试从响应中提取数字（这里简化处理，实际应该更智能）
        Matcher matcher = COURSE_ID_PATTERN.matcher(response);

        while (matcher.find() && courseIds.size() < 10) {
            try {
                int id = Integer.parseInt(matcher.group());
                if (id > 0 && id < 10000) { // 合理的ID范围
                    courseIds.add(id);
                }
            } catch (NumberFormatException ignored) {
            }
        }

        // 如果没有提取到ID，返回默认推荐
        if (courseIds.isEmpty()) {
            log.warn("未能从AI响应中提取课程ID，返回默认推荐");
            courseIds.addAll(List.of(1, 2, 3));
        }

        return courseIds;
    }
}
