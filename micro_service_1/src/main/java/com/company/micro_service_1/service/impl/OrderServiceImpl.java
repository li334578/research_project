package com.company.micro_service_1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.micro_service_1.bean.Order;
import com.company.micro_service_1.mapper.OrderMapper;
import com.company.micro_service_1.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
