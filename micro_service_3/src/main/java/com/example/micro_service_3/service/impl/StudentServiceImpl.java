package com.example.micro_service_3.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.micro_service_3.bean.Student;
import com.example.micro_service_3.mapper.StudentMapper;
import com.example.micro_service_3.service.StudentService;
import org.springframework.stereotype.Service;

/**
 * @Date 30/5/2023 0030 下午 2:38
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
}
