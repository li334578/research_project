package com.company.research_spring.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.company.research_spring.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-09-04 10:03:23
 */
@Service
public class AsyncServiceImpl implements AsyncService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);

    /**
     * 执行异步任务 * 可以根据需求，自己加参数拟定
     */
    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync() {
        System.out.println(RandomUtil.randomString(6));
        System.out.println(Thread.currentThread().getName() + "执行任务中........");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("end executeAsync");
    }
}
