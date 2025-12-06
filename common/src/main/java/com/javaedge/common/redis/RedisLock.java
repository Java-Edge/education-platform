package com.javaedge.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author JavaEdge
 * @date 2023/6/2
 */
@Component
@Slf4j
public class RedisLock {

    private static RedisCrudService redisCrudService;

    @Autowired
    public void setRedisCrudService(RedisCrudService redisCrudService) {
        RedisLock.redisCrudService = redisCrudService;
    }

    public static void lockAndRun(RedisKey redisKey, long lockSeconds, RedisLockHandler redisLockHandler) {
        if (redisCrudService.getLock(redisKey, lockSeconds)) {
            log.info("[lockAndRun]获取锁成功=================={}", redisKey.getKey());
            try {
                redisLockHandler.handle();
            } catch (Exception e) {
                log.error("[lockAndRun] handle error:", e);
            } finally {
                redisCrudService.del(redisKey);
            }
        } else {
            log.info("[lockAndRun]获取锁失败=================={}", redisKey.getKey());
        }
    }

}
