package com.javaedge.common.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AI对话响应模型
 *
 * @author JavaEdge
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiChatResponse {

    /**
     * AI回复内容
     */
    private String content;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 使用的token数
     */
    private Integer tokensUsed;

    /**
     * 响应时间（毫秒）
     */
    private Long responseTime;

    /**
     * 模型名称
     */
    private String modelName;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 错误信息（如果有）
     */
    private String errorMessage;
}
