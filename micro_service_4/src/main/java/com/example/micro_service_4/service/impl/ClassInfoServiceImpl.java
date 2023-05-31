package com.example.micro_service_4.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.micro_service_4.bean.ClassInfo;
import com.example.micro_service_4.mapper.ClassInfoMapper;
import com.example.micro_service_4.service.ClassInfoService;
import org.springframework.stereotype.Service;

/**
 * @Date 30/5/2023 0030 下午 2:38
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Service
public class ClassInfoServiceImpl extends ServiceImpl<ClassInfoMapper, ClassInfo> implements ClassInfoService {
}
