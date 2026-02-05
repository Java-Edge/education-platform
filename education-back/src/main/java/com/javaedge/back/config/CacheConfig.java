package com.javaedge.back.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 缓存配置类
 *
 * @author zqy
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * 配置 Caffeine 作为本地缓存管理器
     */
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                // 设置缓存初始容量（预估公司数量3000）
                .initialCapacity(500)
                // 设置缓存最大容量，留有余量以适应未来增长
                .maximumSize(5000)
                // 设置写入后过期时间，公司列表相对稳定，设置为1小时
                .expireAfterWrite(1, TimeUnit.HOURS)
                // 设置访问后过期时间，经常访问的数据会保持在缓存中
                .expireAfterAccess(30, TimeUnit.MINUTES)
                // 启用缓存统计
                .recordStats());
        return cacheManager;
    }
}
