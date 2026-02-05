package com.javaedge.common.ai.service.impl;

import com.javaedge.common.ai.model.AiChatRequest;
import com.javaedge.common.ai.model.AiChatResponse;
import com.javaedge.common.ai.service.AiChatService;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AI聊天服务实现
 *
 * @author JavaEdge
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AiChatServiceImpl implements AiChatService {

    private final ChatLanguageModel chatLanguageModel;

    // 简单的内存会话存储（生产环境建议使用Redis）
    private final Map<String, List<dev.langchain4j.data.message.ChatMessage>> sessionStore = new ConcurrentHashMap<>();

    @Override
    public AiChatResponse chat(AiChatRequest request) {
        long startTime = System.currentTimeMillis();

        try {
            log.info("处理AI聊天请求 - userId: {}, sessionId: {}, message: {}",
                request.getUserId(), request.getSessionId(), request.getMessage());

            // 构建消息列表
            List<dev.langchain4j.data.message.ChatMessage> messages = new ArrayList<>();

            // 添加系统提示（如果有）
            if (request.getSystemPrompt() != null && !request.getSystemPrompt().isEmpty()) {
                messages.add(SystemMessage.from(request.getSystemPrompt()));
            }

            // 如果有会话ID，加载历史消息
            if (request.getSessionId() != null && !request.getSessionId().isEmpty()) {
                List<dev.langchain4j.data.message.ChatMessage> history = sessionStore.get(request.getSessionId());
                if (history != null) {
                    messages.addAll(history);
                }
            }

            // 添加当前用户消息
            UserMessage userMessage = UserMessage.from(request.getMessage());
            messages.add(userMessage);

            // 调用AI模型
            Response<AiMessage> response = chatLanguageModel.generate(messages);

            String aiContent = response.content().text();

            // 保存到会话历史
            if (request.getSessionId() != null && !request.getSessionId().isEmpty()) {
                messages.add(response.content());
                sessionStore.put(request.getSessionId(), messages);
            }

            long responseTime = System.currentTimeMillis() - startTime;

            log.info("AI聊天完成 - responseTime: {}ms, tokensUsed: {}",
                responseTime, response.tokenUsage() != null ? response.tokenUsage().totalTokenCount() : 0);

            return AiChatResponse.builder()
                    .content(aiContent)
                    .sessionId(request.getSessionId())
                    .tokensUsed(response.tokenUsage() != null ? response.tokenUsage().totalTokenCount() : 0)
                    .responseTime(responseTime)
                    .success(true)
                    .build();

        } catch (Exception e) {
            log.error("AI聊天失败", e);
            return AiChatResponse.builder()
                    .success(false)
                    .errorMessage(e.getMessage())
                    .responseTime(System.currentTimeMillis() - startTime)
                    .build();
        }
    }

    @Override
    public String simpleChat(String message) {
        log.info("简单聊天: {}", message);
        try {
            String response = chatLanguageModel.generate(message);
            log.debug("AI回复: {}", response);
            return response;
        } catch (Exception e) {
            log.error("简单聊天失败", e);
            return "抱歉，AI服务暂时不可用，请稍后再试。";
        }
    }

    @Override
    public String chatWithSystemPrompt(String message, String systemPrompt) {
        log.info("带系统提示的聊天 - systemPrompt: {}, message: {}", systemPrompt, message);

        try {
            List<dev.langchain4j.data.message.ChatMessage> messages = new ArrayList<>();
            messages.add(SystemMessage.from(systemPrompt));
            messages.add(UserMessage.from(message));

            Response<AiMessage> response = chatLanguageModel.generate(messages);
            String aiContent = response.content().text();

            log.debug("AI回复: {}", aiContent);
            return aiContent;

        } catch (Exception e) {
            log.error("带系统提示的聊天失败", e);
            return "抱歉，AI服务暂时不可用，请稍后再试。";
        }
    }

    /**
     * 清除会话历史
     */
    public void clearSession(String sessionId) {
        sessionStore.remove(sessionId);
        log.info("清除会话: {}", sessionId);
    }
}
