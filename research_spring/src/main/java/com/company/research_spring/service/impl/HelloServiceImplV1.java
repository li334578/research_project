package com.company.research_spring.service.impl;

import com.company.research_spring.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @Author: liwenbo
 * @Date: 2021年09月09日
 * @Description:
 */
@Service
public class HelloServiceImplV1 implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("say hello V1");
    }

    @Override
    public void sayHi() {
        System.out.println("say hi V1");
    }
}
