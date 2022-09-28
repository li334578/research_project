package com.example.micro_service_2;

import cn.hippo4j.core.enable.EnableDynamicThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDynamicThreadPool
public class MicroService2Application {

    public static void main(String[] args) {
        SpringApplication.run(MicroService2Application.class, args);
    }

}
