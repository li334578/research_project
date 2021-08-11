package com.company.sharding_sphere.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-07-10 16:03:11
 */
@Configuration
public class RedissonConfig {

    @Bean
    public Redisson redisson() {
        Config config = new Config();
        // 使用单机模式 设置地址 密码 和所用数据库
        config.useSingleServer().setAddress("redis://localhost:6379")
                .setPassword("root").setDatabase(1);
        return (Redisson) Redisson.create(config);
    }
}
