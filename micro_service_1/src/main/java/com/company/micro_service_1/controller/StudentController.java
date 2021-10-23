package com.company.micro_service_1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-10-22 20:36:53
 */
@RestController
@RequestMapping("/student")
public class StudentController {


    @GetMapping("/")
    public void student(){

    }
}
