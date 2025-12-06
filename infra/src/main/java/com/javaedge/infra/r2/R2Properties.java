package com.javaedge.infra.r2;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

/**
 * @author JavaEdge
 * @date 2025/2/27
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "cloudflare.r2")
public class R2Properties {
    private String accessKeyId;
    private String secretAccessKey;
    private String bucketName;
    private String region;
    private String endpoint;
}
