package com.company.research_spring.bean;

import com.company.research_spring.entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-09-11 17:56:19
 */
@Configuration
public class StudentBean {

    @Bean
    public Student student() {
        Student student = new Student();
        student.setName("tom");
        student.setAge(18);
        return student;
    }
}
