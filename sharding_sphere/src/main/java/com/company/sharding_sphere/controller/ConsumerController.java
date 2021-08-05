package com.company.sharding_sphere.controller;

import com.company.sharding_sphere.controller.dto.TestDto;
import com.company.sharding_sphere.entity.animalDemo.AnimalAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: liwenbo
 * @Date: 2021年05月31日
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class ConsumerController {

    @Resource
    List<AnimalAction> animalActionList;

    @GetMapping("/getTest")
    public String testController(@RequestBody TestDto testDto) {
        System.out.println(testDto);
        return "xxxx";
    }

    @GetMapping("/testResource")
    public String testResource() {
        animalActionList.forEach(System.out::println);
        return "success";
    }
}
