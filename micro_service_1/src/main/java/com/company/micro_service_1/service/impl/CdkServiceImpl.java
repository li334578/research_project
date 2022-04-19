package com.company.micro_service_1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.micro_service_1.bean.Cdk;
import com.company.micro_service_1.mapper.CdkMapper;
import com.company.micro_service_1.service.CdkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Date 4/3/2022 0004 上午 11:29
 * @Description cdk service 实现类
 * @Version 1.0.0
 * @Author liwenbo
 */
@Service
public class CdkServiceImpl extends ServiceImpl<CdkMapper, Cdk> implements CdkService {

    @Resource
    private CdkMapper cdkMapper;
}
