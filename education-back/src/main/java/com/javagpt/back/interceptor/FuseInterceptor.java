package com.javagpt.back.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import groovy.util.logging.Slf4j;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author JavaEdge
 * @date 2024/6/14
 */
@Component
@Slf4j
public class FuseInterceptor implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // 设置超时时间
    public static Cache<String, Object> moduleCache = CacheBuilder.
            newBuilder().
            // 写入缓存后，10s后过期
            expireAfterWrite(10, TimeUnit.SECONDS).
            build();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ExecutionException {
        // 先在本地缓存中获取内容，若获取不到，直接查询Redis并放入本地缓存（线程安全）
        String moduleStatus = (String) moduleCache.get("topic:service_xx:module", () -> {
            //若本地缓存获取不到，那么去Redis查询数据并放入缓存。
            String value = stringRedisTemplate.opsForValue().get("service_xx:module");
            return StringUtils.isBlank(value) ? "close" : value;
        });
        // 拦截器处理
        return true;
    }

    /**
     * 订阅发布获取到最新的Redis内容
     *
     * @param type    监听队列的名
     * @param message 监听到的信息
     */
    public void refreshCache(String type, String message) {
        if ("topic:service_xx".equals(type)) {
            //若是这个队列的消息，那么将其转换为Set<String>结构，并放入缓存中
            Set<String> patterns = JSONObject.parseObject(message,
                    new TypeReference<LinkedHashSet<String>>() {
                    }.getType());
            moduleCache.put(type, patterns);
        } else {
            moduleCache.put(type, message);
        }
    }
}