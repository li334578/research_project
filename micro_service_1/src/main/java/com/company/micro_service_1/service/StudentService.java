package com.company.micro_service_1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.micro_service_1.bean.Student;

import java.util.List;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-10-24 19:56:32
 */
public interface StudentService extends IService<Student> {

    List<Student> students();

    Student student(Long id);

}
