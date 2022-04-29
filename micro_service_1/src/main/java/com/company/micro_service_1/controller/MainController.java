package com.company.micro_service_1.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

/**
 * @Classname MainController
 * @Description TODO
 * @Version 1.0.0
 * @Date 27/4/2022 下午 8:07
 * @Created by 李文博
 */
@RestController
@RequestMapping("/main/")
@Slf4j
public class MainController {

    @Resource
    private ExecutorService executorService;

    @Resource
    private RedissonClient redissonClient;
}
