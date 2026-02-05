package com.javaedge.common.ai.service;

import com.javaedge.common.ai.model.AiChatRequest;
import com.javaedge.common.ai.model.AiChatResponse;

/**
 * AI聊天服务接口
 * 提供基础的AI对话能力
 *
 * @author JavaEdge
 */
public interface AiChatService {

    /**
     * 发送消息给AI并获取回复
     *
     * @param request 聊天请求
     * @return AI回复
     */
    AiChatResponse chat(AiChatRequest request);

    /**
     * 简单聊天（无上下文）
     *
     * @param message 用户消息
     * @return AI回复内容
     */
    String simpleChat(String message);

    /**
     * 带系统提示的聊天
     *
     * @param message 用户消息
     * @param systemPrompt 系统提示词
     * @return AI回复内容
     */
    String chatWithSystemPrompt(String message, String systemPrompt);

    /**
     * 流式聊天（支持逐字输出）
     *
     * @param request 聊天请求
     * @return 流式响应
     */
    // Flux<String> streamChat(AiChatRequest request);
}
