package com.company.micro_service_1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.micro_service_1.bean.Student;
import com.company.micro_service_1.mapper.StudentMapper;
import com.company.micro_service_1.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-10-24 19:56:49
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper,Student> implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    @Override
    public List<Student> students() {
        return studentMapper.selectList(null);
    }

    @Override
    public Student student(Long id) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return studentMapper.selectOne(queryWrapper);
    }
}
