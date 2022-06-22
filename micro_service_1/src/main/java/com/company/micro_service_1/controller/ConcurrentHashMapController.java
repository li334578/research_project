package com.company.micro_service_1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
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
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, 10).parallel().forEach(i -> {
            // 十个线程并发的处理数据
            // 查询需要补充的元素
            try {
                reentrantLock.lock(); // 加锁解决 或者使用 synchronized(currentHashMap)
                int gap = ITEM_COUNT - concurrentHashMap.size();
                log.warn("need add item count is {}", gap);
                // 补充元素
                concurrentHashMap.putAll(getData(gap));
                count.countDown();
            } finally {
                reentrantLock.unlock();
            }

        }));
        // 等待所有任务完成
        count.await();
        // 最终元素个数
        log.info("finally item count is {}", concurrentHashMap.size());
        /*
         * tip ConcurrentHashMap 提供了一些原子性的简单复合逻辑方法，用好这些方法就可以发挥其威力。
         * 这就引申出代码中常见的另一个问题：在使用一些类库提供的高级工具类时，
         * 开发人员可能还是按照旧的方式去使用这些新类，因为没有使用其特性，所以无法发挥其威力。
         * */
        return "successfully";
    }

    @GetMapping("/test3")
    public String testConcurrentHashMap3() throws InterruptedException {
        /*
         * 使用 ConcurrentHashMap 来统计，Key 的范围是 10。
         * 使用最多 10 个并发，循环操作 1000 万次，每次操作累加随机的 Key。
         * 如果 Key 不存在的话，首次设置值为 1。
         *
         */
        long start = System.currentTimeMillis();
        testOne();
        log.info("use time is {} ms", System.currentTimeMillis() - start);
        return "successfully";
    }

    private void testOne() throws InterruptedException {
        ConcurrentHashMap<Integer, Long> concurrentHashMap = new ConcurrentHashMap<>(ITEM_COUNT);
        ReentrantLock reentrantLock = new ReentrantLock();

        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        CountDownLatch count = new CountDownLatch(LOOP_COUNT);

        forkJoinPool.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(item -> {
            try {
                reentrantLock.lock();
                int key = ThreadLocalRandom.current().nextInt(ITEM_COUNT);
                if (concurrentHashMap.containsKey(key)) {
                    long value = concurrentHashMap.get(key) + 1;
                    concurrentHashMap.put(key, value);
                } else {
                    concurrentHashMap.put(key, 1L);
                }
                count.countDown();
            } finally {
                reentrantLock.unlock();
            }
        }));
        count.await();
        // 验证key的个数是否等于 ITEM_COUNT
        Assert.isTrue(Objects.equals(ITEM_COUNT, concurrentHashMap.size()), "key count is error");
        // 验证value的和是否等于 LOOP_COUNT
        Assert.isTrue(LOOP_COUNT == concurrentHashMap.values().stream().reduce(0L, Long::sum), "value count is error");
    }

    @GetMapping("/test4")
    public String testConcurrentHashMap4() throws InterruptedException {
        /*
         * 使用 ConcurrentHashMap 来统计，Key 的范围是 10。
         * 使用最多 10 个并发，循环操作 1000 万次，每次操作累加随机的 Key。
         * 如果 Key 不存在的话，首次设置值为 1。
         *
         */
        long start = System.currentTimeMillis();
        return "";
    }

    private void testTwo() throws InterruptedException {

    }
}
