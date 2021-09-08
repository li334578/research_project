package com.company.research_spring.config;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-09-07 20:21:36
 */
public enum ThreadPoolTaskExecutorEnum {

    TheadPool;

//    @Value("${async.executor.thread.core_pool_size}")
//    private int corePoolSize;
//    @Value("${async.executor.thread.max_pool_size}")
//    private int maxPoolSize;
//    @Value("${async.executor.thread.queue_capacity}")
//    private int queueCapacity;
//    @Value("${async.executor.thread.name.prefix}")
//    private String namePrefix;

    ThreadPoolTaskExecutorEnum() {
        executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(20);
        //配置最大线程数
        executor.setMaxPoolSize(100);
        //配置队列大小
        executor.setQueueCapacity(999);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("namePrefix");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
    }

    private final ThreadPoolTaskExecutor executor;


    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        return executor;
    }
}
