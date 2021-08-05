package com.company.research_redission.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-07-31 21:27:55
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
//        config.useSingleServer()
//                .setAddress("redis://172.27.128.1:6380")
//                .setPassword("root");

        config.useClusterServers()
                // 集群状态扫描间隔时间，单位是毫秒
                .setScanInterval(2000)// cluster state scan interval in milliseconds
                //cluster方式至少6个节点(3主3从，3主做sharding，3从用来保证主宕机后可以高可用)
                .addNodeAddress("redis://172.27.128.1:6379", "redis://172.27.128.1:6380")
                .addNodeAddress("redis://172.27.128.1:6381")
                .addNodeAddress("redis://172.27.128.1:6382")
                .addNodeAddress("redis://172.27.128.1:6383")
                .addNodeAddress("redis://172.27.128.1:6384")
                .setPassword("root");
        return Redisson.create(config);
    }
}
