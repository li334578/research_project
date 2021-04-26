package com.company.sharding_sphere.service;

import redis.clients.jedis.Jedis;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-04-25 20:43:42
 */
public interface RedisDataSource {

    Jedis getRedisClient();


    void returnResource(Jedis jedis);

    boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime);

    boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId);
}
