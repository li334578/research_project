package com.company.micro_service_1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * @Classname ConcurrentHashMapController
 * @Description TODO
 * @Version 1.0.0
 * @Date 2/6/2022 下午 9:31
 * @Created by 李文博
 */
@Slf4j
public class ConcurrentHashMapController {

    // 线程数
    private static final int THREAD_COUNT = 10;

    // 元素个数
    private static final int ITEM_COUNT = 1000;

    //循环次数
    private static final int LOOP_COUNT = 10000000;


    private ConcurrentHashMap<String, Long> getData(int count) {
        return LongStream.rangeClosed(1, count).boxed().collect(Collectors.toConcurrentMap(
                i -> UUID.randomUUID().toString(),
                Function.identity(),
                ((o1, o2) -> o1),
                ConcurrentHashMap::new
        ));
    }

    @GetMapping("/test1")
    public String testConcurrentHashMap1() throws InterruptedException {
        // 初始化900个元素
        ConcurrentHashMap<String, Long> concurrentHashMap = getData(ITEM_COUNT - 100);
        log.warn("initial size is {}", concurrentHashMap.size());
        CountDownLatch count = new CountDownLatch(10);

        // 使用线程池并发处理
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, 10).parallel().forEach(i -> {
            // 十个线程并发的处理数据
            // 查询需要补充的元素
            int gap = ITEM_COUNT - concurrentHashMap.size();
            log.warn("need add item count is {}", gap);
            // 补充元素
            concurrentHashMap.putAll(getData(gap));
            count.countDown();
        }));
        // 等待所有任务完成
        count.await();
        // 最终元素个数
        log.info("finally item count is {}", concurrentHashMap.size());

        return "successfully";
    }

    @GetMapping("/test2")
    public String testConcurrentHashMap2() throws InterruptedException {
        // 初始化900个元素
        ConcurrentHashMap<String, Long> concurrentHashMap = getData(ITEM_COUNT - 100);
        log.warn("initial size is {}", concurrentHashMap.size());
        CountDownLatch count = new CountDownLatch(10);

        // 使用线程池并发处理
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        ReentrantLock reentrantLock = new ReentrantLock();
        return "";
    }
}
