package com.javaedge.back.config;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.javaedge.back.entity.Dictionary;
import com.javaedge.back.entity.DictionaryType;
import com.javaedge.back.entity.InterviewEntity;
import com.javaedge.back.entity.Pilot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public Cache<String, List<Pilot>> pilotCache() {
        return Caffeine.newBuilder()
                .maximumSize(1)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build();
    }

    /**
     * 字典表的缓存
     */
    @Bean
    public Cache<String, List<Dictionary>> dictCache() {
        return Caffeine.newBuilder()
                .maximumSize(1)
                .expireAfterWrite(20, TimeUnit.MINUTES)
                .build();
    }

    /**
     * 字典种类表的缓存
     */
    @Bean
    public Cache<String, List<DictionaryType>> dictTypeCache() {
        return Caffeine.newBuilder()
                .maximumSize(1)
                .expireAfterWrite(20, TimeUnit.MINUTES)
                .build();
    }

    /**
     * 面经分页查询缓存
     * 缓存键格式：pageNo-pageSize-jobId
     */
    @Bean
    public Cache<String, IPage<InterviewEntity>> interviewPageCache() {
        return Caffeine.newBuilder()
                // 缓存最多100个不同的分页查询结果
                .maximumSize(100)
                // 5分钟后过期，确保数据相对新鲜
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build();
    }

    /**
     * 面经详情缓存
     * 缓存键：面经ID
     */
    @Bean
    public Cache<String, InterviewEntity> interviewDetailCache() {
        return Caffeine.newBuilder()
                // 缓存最多500个面经详情
                .maximumSize(500)
                // 10分钟后过期
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();
    }
}
