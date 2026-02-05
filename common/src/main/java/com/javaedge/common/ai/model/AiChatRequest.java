package com.javaedge.common.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AI对话请求模型
 *
 * @author JavaEdge
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiChatRequest {

    /**
     * 用户消息
     */
    private String message;

    /**
     * 会话ID（用于保持对话上下文）
     */
    private String sessionId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 系统提示词（可选）
     */
    private String systemPrompt;

    /**
     * 温度参数（0-1，控制随机性）
     */
    private Double temperature;

    /**
     * 最大token数
     */
    private Integer maxTokens;

    /**
     * 上下文（可选，用于提供额外信息）
     */
    private String context;
}
