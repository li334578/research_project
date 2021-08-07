package com.company.research_redission.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

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
//                .setAddress("redis://192.168.232.130:6380")
//                .setPassword("root");

        Set<String> slaveSet = new HashSet<>();
        slaveSet.add("redis://172.30.229.226:6382");
        slaveSet.add("redis://172.30.229.226:6383");
        slaveSet.add("redis://172.30.229.226:6384");

        config.useMasterSlaveServers()
                // 集群状态扫描间隔时间，单位是毫秒
                // cluster state scan interval in milliseconds
                //cluster方式至少6个节点(3主3从，3主做sharding，3从用来保证主宕机后可以高可用)
//                .addNodeAddress("redis://172.30.229.226:6379", "redis://172.30.229.226:6380")
//                .addNodeAddress("redis://172.30.229.226:6381")
//                .addNodeAddress("redis://172.30.229.226:6382")
//                .addNodeAddress("redis://172.30.229.226:6383")
//                .addNodeAddress("redis://172.30.229.226:6384")
                .setMasterAddress("redis://172.30.229.226:6379")
                .setMasterAddress("redis://172.30.229.226:6380")
                .setMasterAddress("redis://172.30.229.226:6381")
                .setPassword("root")
                .setSlaveAddresses(slaveSet);

        return Redisson.create(config);
    }
}
