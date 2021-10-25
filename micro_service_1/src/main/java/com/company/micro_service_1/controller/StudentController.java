package com.company.micro_service_1.controller;

import com.company.micro_service_1.bean.Student;
import com.company.micro_service_1.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Resource
    private StudentService studentService;


    @GetMapping("/")
    public void student(){
        List<Student> students = studentService.students();
        students.forEach(System.out::println);
    }
}
