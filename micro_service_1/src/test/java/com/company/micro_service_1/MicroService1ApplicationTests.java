package com.company.micro_service_1;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MicroService1ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testMethod1() {
        Snowflake snowflake = IdUtil.createSnowflake(1L, 1L);
        System.out.println(snowflake.nextId());
    }
}
