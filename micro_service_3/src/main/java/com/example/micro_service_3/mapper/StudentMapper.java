package com.example.micro_service_3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.micro_service_3.bean.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Date 30/5/2023 0030 下午 2:38
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
