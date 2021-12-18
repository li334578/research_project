package com.company.micro_service_1.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.company.micro_service_1.bean.Order;
import com.company.micro_service_1.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public void getOrder(@PathVariable("id") Integer id) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        orderService.getOne(queryWrapper);
    }

    @DeleteMapping("/")
    public void delOrder(){

    }
}
