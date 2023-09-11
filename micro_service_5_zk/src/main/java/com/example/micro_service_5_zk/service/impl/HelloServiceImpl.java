package com.example.micro_service_5_zk.service.impl;

import com.example.micro_service_5_zk.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @Date 2023-09-11 14:43
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String getHello() {
        return "hello";
    }
}
