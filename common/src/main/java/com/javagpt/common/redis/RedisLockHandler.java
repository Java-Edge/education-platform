package com.javagpt.common.redis;

/**
 * @author JavaEdge
 * @date 2023/6/2
 */
@FunctionalInterface
public interface RedisLockHandler {
    void handle();
}
