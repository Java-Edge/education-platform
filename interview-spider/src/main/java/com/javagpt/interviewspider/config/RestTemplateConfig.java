package com.javagpt.interviewspider.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


/**
 * All rights Reserved, Designed By www.tom.com
 *
 * @Author bubaiwantong
 * @Date 2023/5/8 16:32
 * @PackageName:com.javagpt.interviewspider.config
 * @ClassName: RestTemplateConfig
 * @Description: 请求配置
 * @Version 1.0
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
}
