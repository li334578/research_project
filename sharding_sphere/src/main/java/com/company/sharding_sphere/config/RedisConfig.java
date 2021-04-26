package com.company.sharding_sphere.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-04-25 20:41:23
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource.redis")
public class RedisConfig {
    private String host;
    private int port;
    private String password;
    private int timeout;
    private int maxIdle;
    private long maxWaitMillis;

    @Bean
    public JedisPool redisPoolFactory() {

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
        return jedisPool;
    }
}
