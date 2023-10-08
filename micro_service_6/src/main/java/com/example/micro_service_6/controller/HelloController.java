package com.example.micro_service_6.controller;

import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RQueue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Date 2023-10-07 09:38
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@RequestMapping("/hello/")
@RestController
@Slf4j
public class HelloController {

    @Resource
    private Redisson redisson;

    @GetMapping("world")
    public String helloWorld() {
        return "hello world";
    }

    @GetMapping("init")
    public String initial() {
        // 初始化cdk
        // 生成五百个随机字符串 不重复的
        List<String> cdkList;
        do {
            cdkList = IntStream.rangeClosed(0, 600).mapToObj(i -> RandomUtil.randomStringWithoutStr(10, "o0l1q9p"))
                    .distinct().limit(500).collect(Collectors.toList());
        } while (cdkList.size() < 500);
        // 0 - 9
        List<String> finalCdkList = cdkList;
        IntStream.rangeClosed(0, 49).forEach(item -> {
            RQueue<String> queue = redisson.getQueue("cdk:" + item);
            queue.addAll(finalCdkList.subList(item * 10, (item + 1) * 10));
        });
        return "OK";
    }

    @GetMapping("get")
    public String getCDK(@RequestParam("userId") Long userId) {
        // 分段锁?
        log.info("用户：{}进来开始抢 .....", userId);
        int segment = HashUtil.apHash(String.valueOf(userId)) % 50;
        RLock lock = redisson.getLock("lock" + segment);
        try {
            if (redisson.getBucket("cdk:" + segment).isExists() && lock.tryLock(50, TimeUnit.MILLISECONDS)) {
                // 加锁成功
                RQueue<String> queue = redisson.getQueue("cdk:" + segment);
                // 检索并删除
                String poll = queue.poll();
                // 抢成功了 存数据库
                if (Objects.nonNull(poll)) {
                    // 丢给mq
                    log.info("用户：{} 抢到了cdk {}", userId, poll);
                }
                return poll;
            } else {
                // 加锁失败
                return "请重试";
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
