package com.company.research_spring.entity.controller;

import com.company.research_spring.entity.animalDemo.AnimalAction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: liwenbo
 * @Date: 2021年07月12日
 * @Description:
 */
@RestController
public class testController {
    @Resource
    List<AnimalAction> animalActionList;

    @GetMapping("/testResource")
    public String testResource() {
        animalActionList.forEach(System.out::println);
        return "success";
    }
}
