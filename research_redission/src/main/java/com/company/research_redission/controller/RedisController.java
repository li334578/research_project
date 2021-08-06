package com.company.research_redission.controller;

import com.company.research_redission.controller.dto.RedisPut;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: liwenbo
 * @Date: 2021年08月05日
 * @Description:
 */
@RestController
public class RedisController {
    @Resource
    private RedissonClient redissonClient;

    @PostMapping("/redis/putKeyAndValue")
    public String testMethod(@RequestBody RedisPut redisPut) {
        RBucket<Object> bucket = redissonClient.getBucket(redisPut.getKey());
        bucket.set(redisPut.getValue());
        return "OK";
    }


    @GetMapping("/redis/getKey")
    public String testMethod(@RequestParam("key") String key) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        System.out.println(bucket.get());
        return (String) bucket.get();
    }
}
