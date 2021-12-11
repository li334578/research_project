package com.company.micro_service_1.controller;

import com.company.micro_service_1.service.OrderService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    private OrderService orderService;
}
