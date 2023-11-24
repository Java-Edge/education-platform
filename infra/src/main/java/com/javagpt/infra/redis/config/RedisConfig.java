package com.javagpt.infra.redis.config;

import com.javagpt.infra.redis.RedisCurdTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

/**
 * @author JavaEdge
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisCurdTemplate redisCurdTemplate(StringRedisTemplate stringRedisTemplate) {
        return new RedisCurdTemplate(stringRedisTemplate);
    }

    /**
     * 禁用Redis的config命令后：
     * 这里当配置文件redis.disableConfig=true时
     * 注入ConfigureRedisAction.NO_OP解决该问题，否则按默认处理
     */
    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }
}
