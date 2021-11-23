package com.company.micro_service_1.config;

import cn.hutool.core.util.StrUtil;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-11-21 10:21:34
 */
@Configuration
public class RedissonConfig {
    private static final String redisConfTemp = "redis://{}:{}";

    @Resource
    RedisConfig redisConfig;

    @Bean
    public Redisson redisson() {
        Config config = new Config();
        // 使用单机模式 设置地址 密码 和所用数据库
        config.useSingleServer()
//      config.useMasterSlaveServers()
//              .setMasterAddress("redis://150.230.251.14:9379")
                .setAddress(StrUtil.format(redisConfTemp, redisConfig.getHost(), redisConfig.getPort()))
                .setPassword("root");
        return (Redisson) Redisson.create(config);
    }
}
