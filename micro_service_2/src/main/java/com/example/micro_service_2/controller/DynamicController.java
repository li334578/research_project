package com.example.micro_service_2.controller;

import cn.hippo4j.core.executor.DynamicThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import me.ahoo.cosid.provider.IdGeneratorProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Date 27/9/2022 0027 上午 11:33
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@RequestMapping("/dynamic")
@RestController
@Slf4j
public class DynamicController {

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    @Resource
    private IdGeneratorProvider idGeneratorProvider;

    @GetMapping("/test")
    public String testDynamicThreadPool() {
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(() -> log.info("当前线程名" + Thread.currentThread().getName()));
        }
        return "hello";
    }

    @GetMapping("/get")
    public String getDynamicThreadPoolInfo() {
        // 核心线程数
        int corePoolSize = threadPoolExecutor.getCorePoolSize();
        // 最大线程数
        int maximumPoolSize = threadPoolExecutor.getMaximumPoolSize();
        // 线程池当前线程数 (有锁)
        int poolSize = threadPoolExecutor.getPoolSize();
        // 活跃线程数 (有锁)
        int activeCount = threadPoolExecutor.getActiveCount();
        // 同时进入池中的最大线程数 (有锁)
        int largestPoolSize = threadPoolExecutor.getLargestPoolSize();
        // 线程池中执行任务总数量 (有锁)
        long completedTaskCount = threadPoolExecutor.getCompletedTaskCount();

        log.info("核心线程数:{}", corePoolSize);
        log.info("最大线程数:{}", maximumPoolSize);
        log.info("线程池当前线程数:{}", poolSize);
        log.info("活跃线程数:{}", activeCount);
        log.info("largestPoolSize:{}", largestPoolSize);
        log.info("线程池中执行任务总数量:{}", completedTaskCount);

        BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
        // 队列元素个数
        int queueSize = queue.size();
        // 队列类型
        String queueType = queue.getClass().getSimpleName();
        // 队列剩余容量
        int remainingCapacity = queue.remainingCapacity();
        // 队列容量
        int queueCapacity = queueSize + remainingCapacity;
        log.info("队列元素个数:{}", queueSize);
        log.info("队列类型:{}", queueType);
        log.info("队列剩余容量:{}", remainingCapacity);
        log.info("队列容量:{}", queueCapacity);

        RejectedExecutionHandler rejectedExecutionHandler = threadPoolExecutor instanceof DynamicThreadPoolExecutor
                ? ((DynamicThreadPoolExecutor) threadPoolExecutor).getRedundancyHandler()
                : threadPoolExecutor.getRejectedExecutionHandler();
        log.info("拒绝处理:{}", rejectedExecutionHandler.getClass().getSimpleName());
        long rejectCount = threadPoolExecutor instanceof DynamicThreadPoolExecutor
                ? ((DynamicThreadPoolExecutor) threadPoolExecutor).getRejectCountNum()
                : -1L;
        log.info("拒绝数量:{}", rejectCount);
        return "OK";
    }

    @GetMapping("/generateAsString")
    public String generateAsString() {
        return idGeneratorProvider.getShare().generateAsString();
    }

    @GetMapping("/as-state")
    public Long asState(String id) {
        return idGeneratorProvider.getShare().generate();
    }
}
