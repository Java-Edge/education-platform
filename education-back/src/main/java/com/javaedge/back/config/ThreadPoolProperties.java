package com.javaedge.back.config;

import lombok.Data;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author JavaEdge
 * @date 2024/6/14
 */
@Data
public class ThreadPoolProperties {

    /**
     * 线程池的名称
     */
    private String threadPoolName;

    /**
     * 核心线程数
     */
    private int corePoolSize = 1;

    /**
     * 最大线程数
     */
    private int maximumPoolSize = Runtime.getRuntime().availableProcessors();

    /**
     * 线程队列数
     */
    private int queueCapacity = 1024;

    /**
     * 是否允许核心线程超时
     */
    private boolean allowCoreThreadTimeOut = false;

    /**
     * 超时时间
     */
    private long keepAliveTime = 30;

    /**
     * Timeout unit.
     */
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    public static List<ThreadPoolProperties> initThreadPoolProperties() {
        return null;
    }
}
