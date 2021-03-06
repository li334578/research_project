package com.company.micro_service_1.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.company.micro_service_1.bean.Order;

public interface OrderService extends IService<Order> {

    void delete(QueryWrapper<Order> queryWrapper);
}
