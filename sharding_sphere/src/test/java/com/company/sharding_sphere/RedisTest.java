package com.company.sharding_sphere;

import com.company.sharding_sphere.service.RedisDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void test1() {
        System.out.println(redisDataSource.getRedisClient().set("123", "456"));
    }

    @Test
    public void test2() {
        // 分布式锁
    }
}
