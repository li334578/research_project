package com.company.micro_service_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroService1Application {

    public static void main(String[] args) {
        SpringApplication.run(MicroService1Application.class, args);
    }

}
