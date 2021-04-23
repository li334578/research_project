package com.company.sharding_sphere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description : ShardingSphere
 * @date : 2021-04-21 20:54:18
 */
@SpringBootApplication
@MapperScan("com.company.sharding_sphere.mapper")
public class ShardingSphereApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShardingSphereApplication.class, args);
    }
}
