package com.company.sharding_sphere;

import cn.hutool.core.util.IdUtil;
import com.company.sharding_sphere.service.RedisDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-04-25 20:54:00
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Resource
    private RedisDataSource redisDataSource;
    private Jedis redisClient;

    @Resource
    private Redisson redisson;

    @PostConstruct
    public void initMethod() {
        redisClient = redisDataSource.getRedisClient();
    }

    @Test
    public void test1() {
        System.out.println(redisDataSource.getRedisClient().set("123", "456"));
    }

    @Test
    public void test2() {
        // 分布式锁
        String lockKey = "lockKey";
        String uuidValue = IdUtil.simpleUUID();
        System.out.println(uuidValue);
        // @params 3 NX仅当key不存在的时候设置key XX 直接设置key
        // @params 4 EX 失效时间单位秒 PX 毫秒
        // @return success OK failure null
        try {
            String set1 = redisClient.set(lockKey, uuidValue, "NX", "EX", 10);
            if (set1 == null) {
                // 设置Key失败 相当于没抢占到锁
                return;
            }
            // 执行业务操作
            System.out.println("123");
        } finally {
            if (uuidValue.equals(redisClient.get(lockKey))) {
                // 只释放自己加的锁
                redisClient.del(lockKey);
            }
        }
    }

    public void test3() {
        String lockKey = "lockKey2";
        // 获取锁对象
        RLock rLock = redisson.getLock(lockKey);
        try {
            // 加锁
            rLock.lock();
        } finally {
            // 释放锁
            rLock.unlock();
        }
    }
}
