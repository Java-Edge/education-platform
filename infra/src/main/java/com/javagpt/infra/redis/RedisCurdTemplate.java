package com.javagpt.infra.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author hmy6
 */
public class RedisCurdTemplate {

    /**
     * 其序列化方式
     * this.setKeySerializer(RedisSerializer.string());
     * this.setValueSerializer(RedisSerializer.string());
     * this.setHashKeySerializer(RedisSerializer.string());
     * this.setHashValueSerializer(RedisSerializer.string());
     */
    private StringRedisTemplate redisTemplate;

    public RedisCurdTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    public void set(final String key, final String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Set<String> getKeys(final String key) {
        Set<String> keys = redisTemplate.keys(key);
        return keys;
    }

    public String getSet(final String key, final String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    public void setEx(final String key, final long seconds, final String value) {
        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    public Boolean setNx(final String key, final String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public Boolean expire(final String key, final long seconds) {
        return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    public long ttl(final String key) {
        return redisTemplate.getExpire(key);
    }

    public void del(final String key) {
        redisTemplate.delete(key);
    }

    public void hSet(final String key, final String field, final String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    public void hSetAll(final String key, final Map<String, String> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public Long hLen(final String key) {
        return redisTemplate.opsForHash().size(key);
    }

    public Object hGet(final String key, final String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    public Map<Object, Object> hGetAll(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public Boolean zAdd(final String key, final Double score, final String member) {
        return redisTemplate.opsForZSet().add(key, member, score);
    }

    public Set<TypedTuple<String>> zReverseRangeWithScores(final String key) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, -1);
    }

    public Set<String> zReverseRange(final String key) {
        return redisTemplate.opsForZSet().reverseRange(key, 0, -1);
    }

    public Double zIncrBy(final String key, final Double score, final String member) {
        return redisTemplate.opsForZSet().incrementScore(key, member, score);
    }

    public Long lLen(final String key) {
        return redisTemplate.opsForList().size(key);
    }

    public Long lPush(final String key, final String... strings) {
        return redisTemplate.opsForList().leftPushAll(key, strings);
    }

    /**
     * 移除并返回列表 key 的尾元素。
     *
     * @param key
     * @return
     */
    public String rPop(final String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    public String brPop(final String key) {
        return redisTemplate.opsForList().rightPop(key, 0, TimeUnit.SECONDS);
    }


    /**
     * 递减操作
     *
     * @param key
     * @param by
     * @return
     */
    public double decr(String key, double by) {
        return redisTemplate.opsForValue().increment(key, -by);
    }

    /**
     * 递增操作
     *
     * @param key
     * @param by
     * @return
     */
    public double incr(String key, double by) {
        return redisTemplate.opsForValue().increment(key, by);
    }

    public long incr(String key, long by) {
        return redisTemplate.opsForValue().increment(key, by);
    }

    public long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public long hIncr(String key, String field, long by) {
        return redisTemplate.opsForHash().increment(key, field, by);
    }

    /**
     * 获取分布式锁
     *
     * @param lockKey  key
     * @param lockTime 锁有效时间
     * @return
     */
    public boolean getLock(String lockKey, long lockTime) {
        long expires = System.currentTimeMillis() + lockTime * 1000 + 1;
        String expiresStr = String.valueOf(expires);
        if (this.setNx(lockKey, expiresStr)) {
            return true;
        }
        String currentValueStr = get(lockKey);
        if (StringUtils.isNotEmpty(currentValueStr) && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
            String oldValueStr = getSet(lockKey, expiresStr);
            if (StringUtils.isNotEmpty(oldValueStr) && oldValueStr.equals(currentValueStr)) {
                return true;
            }
        }
        return false;
    }
}