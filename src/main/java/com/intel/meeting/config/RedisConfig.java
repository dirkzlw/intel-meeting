package com.intel.meeting.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis配置类
 *
 * @author Ranger
 * @create 2019-09-03 16:42
 */
@Configuration    //spring boot启动时加载此类
public class RedisConfig {

    /**
     * 1.创建jedisPoolConfig对象，在该对象中完成一些连接池的配置
     */
    @Bean    //同<bean></bean>
    @ConfigurationProperties(prefix = "spring.redis.pool")
    public JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();

        return config;
    }

    /**
     * 2.配置jedisConnectionFactory：配置redis连接信息
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.redis")   //指定application.properties中的配置信息
    public JedisConnectionFactory getJedisConnectionFactory(JedisPoolConfig config) {
        JedisConnectionFactory factory = new JedisConnectionFactory();

        //关联连接池的配置对象
        factory.setPoolConfig(config);
        //设定操作哪个库（16个当中的）
        //factory.setDatabase(0);

        return factory;
    }

    /**
     * 3.创建RedisTemplate：用于执行Redis操作的方法
     */
    @Bean
    public RedisTemplate<String, Object> getRedisTemplate(JedisConnectionFactory factory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();

        //关联
        template.setConnectionFactory(factory);

        //为key设置序列化器
        template.setKeySerializer(new StringRedisSerializer());

        //为value设置序列化器
        template.setValueSerializer(new StringRedisSerializer());

        return template;
    }
}
