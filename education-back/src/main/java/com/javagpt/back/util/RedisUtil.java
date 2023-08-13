package com.javagpt.back.util;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // 限流LUA脚本
    static ResourceScriptSource rateLimitLUA = new ResourceScriptSource(new ClassPathResource("file" + File.separator + "RateLimit.lua"));
    static ResourceScriptSource keyExpireLUA = new ResourceScriptSource(new ClassPathResource("file" + File.separator + "KeyExpire.lua"));

    public void set(String key, String value) {
        try {
            stringRedisTemplate.opsForValue().set(key, value);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(stringRedisTemplate.getConnectionFactory()));
        }
    }

    public void setBit(String key, Long offset) {
        try {
            stringRedisTemplate.opsForValue().setBit(key, offset, true);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(stringRedisTemplate.getConnectionFactory()));
        }
    }

    public Boolean getBit(String key, Long offset) {
        try {
            return stringRedisTemplate.opsForValue().getBit(key, offset);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(stringRedisTemplate.getConnectionFactory()));
        }
    }

    public void set(String key, String value, long time, TimeUnit timeUnit) {
        try {
            stringRedisTemplate.opsForValue().set(key, value, time, timeUnit);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(stringRedisTemplate.getConnectionFactory()));
        }
    }

    public void addSet(String key, String value) {
        try {
            stringRedisTemplate.opsForSet().add(key, value);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(stringRedisTemplate.getConnectionFactory()));
        }
    }

    public Boolean getSet(String key, String value) {
        try {
            return stringRedisTemplate.opsForSet().isMember(key, value);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(stringRedisTemplate.getConnectionFactory()));
        }
    }

    public void delete(String key) {
        try {
            stringRedisTemplate.delete(key);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(stringRedisTemplate.getConnectionFactory()));
        }
    }

    public void batchDelete(String key) {
        try {
            stringRedisTemplate.delete(Objects.requireNonNull(stringRedisTemplate.keys(key + "*")));
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(stringRedisTemplate.getConnectionFactory()));
        }
    }

    public String get(String key) {
        try {
            return stringRedisTemplate.opsForValue().get(key);
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(stringRedisTemplate.getConnectionFactory()));
        }
    }

    public Set<String> getMatch(String key) {
        try {
            return stringRedisTemplate.keys(key + "*");
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(stringRedisTemplate.getConnectionFactory()));
        }
    }

    public void setHash(String key, Object field, Object value) {
        try {
            stringRedisTemplate.opsForHash().put(key, String.valueOf(field), String.valueOf(value));
            stringRedisTemplate.expire(key, 3, TimeUnit.DAYS);
        } finally {
            // 手动释放连接
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(stringRedisTemplate.getConnectionFactory()));
        }
    }

    public void delHash(String key, Object field) {
        try {
            stringRedisTemplate.opsForHash().delete(key, String.valueOf(field));
        } finally {
            // 手动释放连接
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(stringRedisTemplate.getConnectionFactory()));
        }
    }

    public String getHash(String key, Object field) {
        try {
            Object o = stringRedisTemplate.opsForHash().get(key, String.valueOf(field));
            if (o == null) return null;
            return o.toString();
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(stringRedisTemplate.getConnectionFactory()));
        }
    }

    /**
     * 用于实现接口限频
     *
     * @param key        限頻key
     * @param limit      限制次数
     * @param expireTime 单位时间（秒）
     * @return true表示可以訪問， false表示已達最大次數
     */
    public boolean checkRateLimit(String key, int limit, int expireTime) {
        try {
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
            redisScript.setResultType(Long.class);
            redisScript.setScriptSource(rateLimitLUA);
            Object result = redisTemplate.execute(redisScript, Collections.singletonList(key), expireTime, limit);
            if (result == null) {
                log.error("RedisUtil.checkRateLimit execute redisScript return err");
                return false;
            }
            return (long) result == 1;
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        }
    }

    public boolean keyExpire(String key, int expireTime) {
        try {
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
            redisScript.setResultType(Long.class);
            redisScript.setScriptSource(keyExpireLUA);
            Object result = redisTemplate.execute(redisScript, Collections.singletonList(key), expireTime);
            if (result == null) {
                log.error("RedisUtil.keyExpire execute redisScript return err");
                return false;
            }
            return (long) result == 1;
        } finally {
            RedisConnectionUtils.unbindConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        }
    }
}
