package com.javagpt.back.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.javagpt.back.entity.Dictionary;
import com.javagpt.back.entity.DictionaryType;
import com.javagpt.back.entity.Pilot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CaffeineConfig {

    /**
     * 声明统一缓存bean，所有数据可共用本cache
     */
    @Bean
    public Cache<String, Object> cache() {
        return Caffeine.newBuilder()
                // 初始的缓存空间大小
                .initialCapacity(50)
                .maximumSize(1000)
                .build();
    }

    /**
     * 设置导航页面工具列表的缓存
     */
    @Bean
    public Cache<Integer, List<Pilot>> pilotRefreshCache() {
        return Caffeine.newBuilder()
                .maximumSize(1)
                .build();
    }

    /**
     * 字典表的缓存
     */
    @Bean
    public Cache<Integer, List<Dictionary>> dictRefreshCache() {
        return Caffeine.newBuilder()
                .maximumSize(1)
                .build();
    }

    /**
     * 字典种类表的缓存
     */
    @Bean
    public Cache<Integer, List<DictionaryType>> dictTypeRefreshCache() {
        return Caffeine.newBuilder()
                .maximumSize(1)
                .build();
    }
}
