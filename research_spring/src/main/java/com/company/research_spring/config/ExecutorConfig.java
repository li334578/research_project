package com.company.research_spring.config;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-09-04 09:54:49
 */
@Configuration
@EnableAsync
@Data
public class ExecutorConfig implements EnvironmentPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);

    @Resource
    private ThreadPoolTaskExecutor executor;

    @Bean(name = "asyncServiceExecutor")
    public Executor asyncServiceExecutor() {
        return executor;
    }


    @Bean(name = "executorService")
    public ExecutorService asyncExecutorService() {
        return executor.getThreadPoolExecutor();
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        System.out.println("postProcessEnvironment launch .... ");
        String name = environment.getProperty("spring.boot.name");
        System.out.println(name);
        String testCoreNum = environment.getProperty("testCoreNum");
        System.out.println(testCoreNum);
    }
}
