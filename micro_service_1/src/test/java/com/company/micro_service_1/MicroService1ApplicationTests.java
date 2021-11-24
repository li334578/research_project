package com.company.micro_service_1;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class MicroService1ApplicationTests {
    @Resource
    private Redisson redisson;

    @Test
    void contextLoads() {
    }

    @Test
    public void testMethod1() {
        Snowflake snowflake = IdUtil.createSnowflake(1L, 1L);
        System.out.println(snowflake.nextId());
    }

    @Test
    public void testMethod2() {
        RBucket<String> nameRBucket = redisson.getBucket("name");
        // 只设置value，key不过期
        nameRBucket.set("四哥");
        // 设置value和key的有效期
        nameRBucket.set("四哥", 30, TimeUnit.SECONDS);
        // 通过key获取value
        System.out.println(redisson.getBucket("name").get());
    }
}
