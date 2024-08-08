package com.javagpt.back.config;

import com.javagpt.back.interceptor.FuseInterceptor;
import com.javagpt.infra.redis.RedisCurdTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * @author JavaEdge
 */
@Configuration
@Slf4j
public class RedisConfig {

    @Bean
    public RedisCurdTemplate redisCurdTemplate(StringRedisTemplate stringRedisTemplate) {
        return new RedisCurdTemplate(stringRedisTemplate);
    }

    /**
     * 禁用Redis的config命令后：
     * 这里当配置文件redis.disableConfig=true时
     * 注入ConfigureRedisAction.NO_OP解决该问题，否则按默认处理
     */
    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //设置监听的队列，可以设置多个
        container.addMessageListener(listenerAdapter, new PatternTopic("topic:service_xx:module"));
        container.addMessageListener(listenerAdapter, new PatternTopic("topic:service_xx"));
        return container;
    }

    //FuseInterceptor是一个普通的Bean对象，他的作用是处理监听到的内容
    @Bean
    MessageListenerAdapter listenerAdapter(FuseInterceptor receiver) {
        return new MessageListenerAdapter(new MessageListener() {
            public void onMessage(Message message, byte[] pattern) {
                //监听的队列信息
                String type = new String(message.getChannel(), StandardCharsets.UTF_8);
                //发送的内容信息
                String m = new String(message.getBody(), StandardCharsets.UTF_8);
                log.info("redis监听到[{}]的消息[{}]", type, m);
                receiver.refreshCache(type, m);
            }
        });
    }

    public RedisCacheConfiguration customProtoStuffRedisCacheConfiguration(Duration ttl) {
        //设置序列化格式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        return RedisCacheConfiguration.
                defaultCacheConfig().serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer)).
                computePrefixWith(cacheName -> "lianyin:" + cacheName + ":").   //设置Cache的前缀，默认::
                // SpringCache中的RedisCache默认运行将Null值存入到缓存中
                // 可通过disableCachingNullValues()方法关闭此设置。
//                disableCachingNullValues().   //若返回值为null，则不允许存储到Cache中
        entryTtl(ttl);  //设置缓存的超时时间
    }
}