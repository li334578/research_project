package com.company.micro_service_1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.micro_service_1.bean.Order;
import com.company.micro_service_1.mapper.OrderMapper;
import com.company.micro_service_1.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public void delete(QueryWrapper<Order> queryWrapper) {
        orderMapper.delete(queryWrapper);
    }
}
