package com.javaedge.infra.redis;

import com.javaedge.common.redis.RedisCrudService;
import com.javaedge.common.redis.RedisKey;
import com.javaedge.common.util.ConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author JavaEdge
 */
@Service
@RequiredArgsConstructor
public class RedisCrudServiceImpl implements RedisCrudService {

    private final RedisCurdTemplate redisCurdTemplate;

    /**
     * 保存
     *
     * @param redisKey
     * @param value
     */
    public void set(RedisKey redisKey, Object value) {
        set(redisKey, value, null);
    }

    /**
     * 保存
     *
     * @param redisKey
     * @param value
     * @param seconds  过期时间 为null表示永不过期
     */
    public void set(RedisKey redisKey, Object value, Long seconds) {
        Objects.requireNonNull(value);
        String valueStr;
        valueStr = ConvertUtils.objToString(value);
        String key = redisKey.getKey();
        if (seconds == null) {
            redisCurdTemplate.set(key, valueStr);
        } else if (seconds > 0) {
            redisCurdTemplate.setEx(key, seconds, valueStr);
        }
    }

    /**
     * 单行保存map类型的值
     *
     * @param redisKey
     * @param key
     * @param value
     */
    public void setMapItem(RedisKey redisKey, String key, Object value) {
        String valueStr = ConvertUtils.objToString(value);
        redisCurdTemplate.hSet(redisKey.getKey(), key, valueStr);
    }

    /**
     * 保存map类型的数据
     *
     * @param redisKey
     * @param map      由于使用的是StringRedisTemplate 所以map的值只能为String，但是这里做了一个特殊的转换
     */
    public void setMap(RedisKey redisKey, Map<String, Object> map) {
        final Map<String, String> param = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            param.put(entry.getKey(), ConvertUtils.objToString(entry.getValue()));
        }
        redisCurdTemplate.hSetAll(redisKey.getKey(), param);
    }


    /**
     * 获取redis的值
     *
     * @param redisKey
     * @return
     */
    public String get(RedisKey redisKey) {
        return get(redisKey, String.class);
    }

    /**
     * 获取redis的值
     *
     * @param redisKey
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T get(RedisKey redisKey, Class<T> tClass) {
        String key = redisKey.getKey();
        String result = redisCurdTemplate.get(key);
        if (result == null) {
            return null;
        }
        return ConvertUtils.strToObject(result, tClass);
    }

    /**
     * 获取map的值
     *
     * @param redisKey
     * @param key
     * @return
     */
    public String getMapItem(RedisKey redisKey, String key) {
        return getMapItem(redisKey, key, String.class);
    }

    /**
     * 获取map的值
     *
     * @param redisKey
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T getMapItem(RedisKey redisKey, String key, Class<T> tClass) {
        String result = (String) redisCurdTemplate.hGet(redisKey.getKey(), key);
        return ConvertUtils.strToObject(result, tClass);
    }

    /**
     * 获取整个map的值
     *
     * @param redisKey
     * @return
     */
    public Map<String, String> getMap(RedisKey redisKey) {
        Map<Object, Object> objectMap = redisCurdTemplate.hGetAll(redisKey.getKey());
        Map<String, String> result = new HashMap<>();
        if (objectMap == null) {
            return result;
        }
        for (Map.Entry<Object, Object> entry : objectMap.entrySet()) {
            result.put(String.valueOf(entry.getKey()), entry.getValue() == null ? null : String.valueOf(entry.getValue()));
        }
        return result;
    }

    /**
     * 获取列表
     *
     * @param redisKey
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> List<T> getList(RedisKey redisKey, Class<T> tClass) {
        String key = redisKey.getKey();
        String result = redisCurdTemplate.get(key);
        if (result == null) {
            return new ArrayList<>();
        }
        List<T> ts = ConvertUtils.toList(result, tClass);
        if (ts == null) {
            return new ArrayList<>();
        }
        return ts;
    }

    /**
     * 给redis key设置过期时间
     *
     * @param redisKey
     * @param seconds
     */
    public void expire(RedisKey redisKey, long seconds) {
        redisCurdTemplate.expire(redisKey.getKey(), seconds);
    }

    /**
     * 获取锁
     *
     * @param redisKey
     * @param seconds
     * @return
     */
    public boolean getLock(RedisKey redisKey, long seconds) {
        return redisCurdTemplate.getLock(redisKey.getKey(), seconds);
    }

    /**
     * 自增，如果key不存在自动创建
     *
     * @param redisKey
     * @return
     */
    public long incr(RedisKey redisKey) {
        return redisCurdTemplate.incr(redisKey.getKey());
    }

    /**
     * map自增
     *
     * @param redisKey
     * @param key
     * @return
     */
    public long incrMapItem(RedisKey redisKey, String key) {
        return redisCurdTemplate.hIncr(redisKey.getKey(), key, 1L);
    }

    @Override
    public void del(RedisKey redisKey) {
        redisCurdTemplate.del(redisKey.getKey());
    }

}
