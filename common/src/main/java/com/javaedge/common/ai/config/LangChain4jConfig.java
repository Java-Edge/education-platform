package com.javaedge.common.ai.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.azure.AzureOpenAiChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * LangChain4j AI配置类
 * 支持多种AI模型提供商：OpenAI、Azure OpenAI、Ollama
 *
 * @author JavaEdge
 */
@Slf4j
@Configuration
public class LangChain4jConfig {

    @Value("${langchain4j.provider:openai}")
    private String provider;

    @Value("${langchain4j.openai.api-key:}")
    private String openaiApiKey;

    @Value("${langchain4j.openai.model-name:gpt-4o-mini}")
    private String openaiModelName;

    @Value("${langchain4j.openai.base-url:https://api.openai.com/v1}")
    private String openaiBaseUrl;

    @Value("${langchain4j.azure.api-key:}")
    private String azureApiKey;

    @Value("${langchain4j.azure.endpoint:}")
    private String azureEndpoint;

    @Value("${langchain4j.azure.deployment-name:gpt-4}")
    private String azureDeploymentName;

    @Value("${langchain4j.ollama.base-url:http://localhost:11434}")
    private String ollamaBaseUrl;

    @Value("${langchain4j.ollama.model-name:qwen2.5:7b}")
    private String ollamaModelName;

    @Value("${langchain4j.timeout:60}")
    private Integer timeout;

    @Value("${langchain4j.temperature:0.7}")
    private Double temperature;

    @Value("${langchain4j.max-tokens:2000}")
    private Integer maxTokens;

    /**
     * 配置聊天语言模型
     * 根据配置的provider选择不同的AI模型提供商
     */
    @Bean
    public ChatLanguageModel chatLanguageModel() {
        log.info("初始化LangChain4j ChatLanguageModel, provider: {}", provider);

        return switch (provider.toLowerCase()) {
            case "openai" -> createOpenAiModel();
            case "azure" -> createAzureModel();
            case "ollama" -> createOllamaModel();
            default -> {
                log.warn("未知的provider: {}, 使用默认OpenAI配置", provider);
                yield createOpenAiModel();
            }
        };
    }

    /**
     * 创建OpenAI模型
     */
    private ChatLanguageModel createOpenAiModel() {
        if (openaiApiKey == null || openaiApiKey.isEmpty()) {
            log.warn("OpenAI API Key未配置，AI功能可能无法正常使用");
        }

        return OpenAiChatModel.builder()
                .apiKey(openaiApiKey)
                .modelName(openaiModelName)
                .baseUrl(openaiBaseUrl)
                .temperature(temperature)
                .maxTokens(maxTokens)
                .timeout(Duration.ofSeconds(timeout))
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    /**
     * 创建Azure OpenAI模型
     */
    private ChatLanguageModel createAzureModel() {
        if (azureApiKey == null || azureApiKey.isEmpty()) {
            log.warn("Azure OpenAI API Key未配置");
        }

        return AzureOpenAiChatModel.builder()
                .apiKey(azureApiKey)
                .endpoint(azureEndpoint)
                .deploymentName(azureDeploymentName)
                .temperature(temperature)
                .maxTokens(maxTokens)
                .timeout(Duration.ofSeconds(timeout))
                .logRequestsAndResponses(true)
                .build();
    }

    /**
     * 创建Ollama本地模型
     */
    private ChatLanguageModel createOllamaModel() {
        log.info("使用Ollama本地模型: {}, baseUrl: {}", ollamaModelName, ollamaBaseUrl);

        return OllamaChatModel.builder()
                .baseUrl(ollamaBaseUrl)
                .modelName(ollamaModelName)
                .temperature(temperature)
                .timeout(Duration.ofSeconds(timeout))
                .build();
    }

    /**
     * 配置嵌入模型（用于向量化文本）
     * 使用本地all-MiniLM-L6-v2模型，无需API Key
     */
    @Bean
    @ConditionalOnProperty(name = "langchain4j.embedding.enabled", havingValue = "true", matchIfMissing = true)
    public EmbeddingModel embeddingModel() {
        log.info("初始化LangChain4j EmbeddingModel (All-MiniLM-L6-V2)");
        return new AllMiniLmL6V2EmbeddingModel();
    }
}
