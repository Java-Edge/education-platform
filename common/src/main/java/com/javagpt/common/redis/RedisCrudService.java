package com.javagpt.common.redis;

import java.util.List;
import java.util.Map;

/**
 * @author JavaEdge
 */
public interface RedisCrudService {

    /**
     * 保存
     *
     * @param redisKey
     * @param value
     */
    void set(RedisKey redisKey, Object value);

    /**
     * 保存
     *
     * @param redisKey
     * @param value
     * @param seconds  过期时间 null表示永不过期
     */
    void set(RedisKey redisKey, Object value, Long seconds);

    /**
     * 单行保存map类型的值
     *
     * @param redisKey
     * @param key
     * @param value
     */
    void setMapItem(RedisKey redisKey, String key, Object value);

    /**
     * 保存map类型的数据
     *
     * @param redisKey
     * @param map      由于使用的是StringRedisTemplate 所以map的值只能为String，但是这里做了一个特殊的转换
     */
    void setMap(RedisKey redisKey, Map<String, Object> map);


    /**
     * 获取redis的值
     *
     * @param redisKey
     * @return
     */
    String get(RedisKey redisKey);

    /**
     * 获取redis的值
     *
     * @param redisKey
     * @param tClass
     * @param <T>
     * @return
     */
    <T> T get(RedisKey redisKey, Class<T> tClass);

    /**
     * 获取map的值
     *
     * @param redisKey
     * @param key
     * @return
     */
    String getMapItem(RedisKey redisKey, String key);

    /**
     * 获取map的值
     *
     * @param redisKey
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    <T> T getMapItem(RedisKey redisKey, String key, Class<T> tClass);

    /**
     * 获取整个map的值
     *
     * @param redisKey
     * @return
     */
    Map<String, String> getMap(RedisKey redisKey);

    /**
     * 获取列表
     *
     * @param redisKey
     * @param tClass
     * @param <T>
     * @return
     */
    <T> List<T> getList(RedisKey redisKey, Class<T> tClass);

    /**
     * 给redis key设置过期时间
     *
     * @param redisKey
     * @param seconds
     */
    void expire(RedisKey redisKey, long seconds);

    /**
     * 获取锁
     *
     * @param redisKey
     * @param seconds
     * @return
     */
    boolean getLock(RedisKey redisKey, long seconds);

    /**
     * 自增，如果key不存在自动创建
     *
     * @param redisKey
     * @return
     */
    long incr(RedisKey redisKey);

    /**
     * map自增
     *
     * @param redisKey
     * @param key
     * @return
     */
    long incrMapItem(RedisKey redisKey, String key);

    void del(RedisKey redisKey);
}
