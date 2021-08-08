package com.company.research_redission.controller;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-07-31 21:34:23
 */
@RestController
public class EmployeeController {

    @Resource
    private RedissonClient redissonClient;

    @RequestMapping("/get")
    public String get() {
        //首先获取redis中的key-value对象，key不存在没关系
        RBucket<String> keyObject = redissonClient.getBucket("key");
        //如果key存在，就设置key的值为新值value
        //如果key不存在，就设置key的值为value
        keyObject.set("value");
        //最后关闭RedissonClient
        redissonClient.shutdown();
        return "success";
    }
}
