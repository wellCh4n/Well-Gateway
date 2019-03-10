package com.wellch4n.boot.config;

import com.wellch4n.service.env.EnvironmentContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * @author wellCh4n
 * @description
 * @create 2019/03/10 19:28
 * 下周我就努力工作
 */

@Configuration
@ComponentScan(basePackages = {"com.wellch4n"})
public class RedisConfig {

    @Autowired
    private EnvironmentContext context;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(context.getRedisHost());
        redisStandaloneConfiguration.setPort(context.getRedisPort());
        redisStandaloneConfiguration.setPassword(context.getRedisPassword());
        redisStandaloneConfiguration.setDatabase(context.getRedisDbIndex());

        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setEnableTransactionSupport(true);

        return redisTemplate;
    }
}
