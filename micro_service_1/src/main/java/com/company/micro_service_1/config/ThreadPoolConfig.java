package com.company.micro_service_1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-11-18 20:03:17
 */
@Configuration
public class ThreadPoolConfig {
    int coreThreads = Runtime.getRuntime().availableProcessors();

    int DEFAULT_CORE = coreThreads + 1;

    int MAX_THREAD = coreThreads << 1;


    @Bean
    public ExecutorService executorService() {
        return new ThreadPoolExecutor(DEFAULT_CORE, MAX_THREAD,
                60L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
