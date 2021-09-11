package com.company.research_spring.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.company.research_spring.entity.Student;
import com.company.research_spring.entity.animalDemo.AnimalAction;
import com.company.research_spring.service.AsyncService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @Author: liwenbo
 * @Date: 2021年07月12日
 * @Description:
 */
@RestController
public class TestController {
    @Resource
    List<AnimalAction> animalActionList;

    @Resource
    private AsyncService asyncService;

    @Resource
    private ExecutorService executorService;

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private Student student;

    @GetMapping("/testResource")
    @SentinelResource("/testResource")
    public String testResource() {
        animalActionList.forEach(item -> {
            System.out.println(item);
            System.out.println("======");
            item.printName();
            System.out.println("======");
            item.voice();
        });
        return "success";
    }


    @GetMapping("/asyncMethod")
    public String asyncMethod() {
        asyncService.executeAsync();
        return "OK";
    }


    @GetMapping("/asyncMethod2")
    public String asyncMethod2() {
        for (int i = 0; i < 80; i++) {
            executorService.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "正在执行任务...");
                System.out.println(RandomUtil.randomString(6));
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        return "OK";
    }

    @GetMapping("/testBeanPostProcessor")
    public void testBeanPostProcessor() {
        Object student1 = applicationContext.getBean("student");
        System.out.println(student1);
        System.out.println("......... cut-off rule ........");
        System.out.println(student);
    }
}
