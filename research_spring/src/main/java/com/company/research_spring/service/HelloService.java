package com.company.research_spring.service;

import com.company.research_spring.anno.RoutingSwitch;

/**
 * @Author: liwenbo
 * @Date: 2021年09月09日
 * @Description:
 */
@RoutingSwitch("hello")
public interface HelloService {

    @RoutingSwitch("V1")
    void sayHello();

    @RoutingSwitch("V2")
    void sayHi();
}
