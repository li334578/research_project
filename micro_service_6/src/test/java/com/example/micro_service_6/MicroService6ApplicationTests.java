package com.example.micro_service_6;

import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MicroService6ApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    public void testMethod1() {
        IdUtil.getSnowflake().nextId();
    }
}
