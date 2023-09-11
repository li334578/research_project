package com.example.micro_service_5_zk.controller;

import com.example.micro_service_5_zk.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Date 2023-09-11 14:39
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@RestController
@RequestMapping("/java/")
public class JavaController {

    @Resource
    private HelloService helloService;

    @GetMapping("hello")
    public String getHello() {
        System.out.println(helloService.getHello());
        return "hello Java";
    }
}
