package com.company.micro_service_1.controller;

import com.company.micro_service_1.bean.Order;
import com.company.micro_service_1.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/")
    public void getOrderList() {
        List<Order> list = orderService.list();
        list.forEach(System.out::println);
    }
}
