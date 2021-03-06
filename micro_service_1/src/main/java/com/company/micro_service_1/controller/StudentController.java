package com.company.micro_service_1.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.copier.Copier;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.company.micro_service_1.bean.Student;
import com.company.micro_service_1.dto.StudentDto;
import com.company.micro_service_1.service.StudentService;
import com.company.micro_service_1.util.CglibBeanCopierUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-10-22 20:36:53
 */
@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

    public static final Snowflake snowFlake1 = IdUtil.getSnowflake(1, 1);
    public static final Copier<Student> studentCopier = () -> {
        Student student = new Student();
        student.setId(snowFlake1.nextId());
        return student;
    };
    @Resource
    private StudentService studentService;
    @Resource
    private Redisson redisson;


    @GetMapping("/")
    public void student() {
        List<Student> students = studentService.students();
        students.forEach(System.out::println);
    }


    @GetMapping("/{id}")
    public void student(@PathVariable("id") Long id) {
        Student student = studentService.student(id);
        System.out.println(student);
    }

    @PutMapping("/")
    public void student(@RequestBody StudentDto studentDto) {
        Student copy = studentCopier.copy();
        BeanUtil.copyProperties(studentDto, copy);
        studentService.save(copy);
    }

    @PostMapping("/")
    public void updateStudent(@RequestBody StudentDto studentDto) {
        Student bean = new Student();
        CglibBeanCopierUtils.copyProperties(studentDto, bean);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", studentDto.getId());
        studentService.update(bean, queryWrapper);
    }
}
