package com.company.micro_service_1.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.micro_service_1.bean.Order;
import com.company.micro_service_1.bean.Receive;
import com.company.micro_service_1.mapper.OrderMapper;
import com.company.micro_service_1.mapper.ReceiveMapper;
import com.company.micro_service_1.service.OrderService;
import com.company.micro_service_1.service.ReceiveService;
import org.springframework.stereotype.Service;

/**
 * @Date 4/3/2022 0004 下午 2:20
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Service
public class ReceiveServiceImpl extends ServiceImpl<ReceiveMapper, Receive> implements ReceiveService {
}
