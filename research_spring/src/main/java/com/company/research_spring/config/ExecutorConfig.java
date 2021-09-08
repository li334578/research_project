package com.company.research_spring.config;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-09-04 09:54:49
 */
@Configuration
@EnableAsync
@Data
public class ExecutorConfig {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);



    @Bean(name = "asyncServiceExecutor")
    public Executor asyncServiceExecutor() {
        return ThreadPoolTaskExecutorEnum.TheadPool.getThreadPoolTaskExecutor();
    }


    @Bean(name = "executorService")
    public ExecutorService asyncExecutorService() {
        return ThreadPoolTaskExecutorEnum.TheadPool.getThreadPoolTaskExecutor().getThreadPoolExecutor();
    }
}
