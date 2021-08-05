package com.company.research_redission.controller;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/test1")
    public String testMethod(@RequestParam("key") String key, @RequestParam("value") String value) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(value);
        return "OK";
    }
}
