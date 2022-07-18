package com.company.micro_service_1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache/")
public class CachePenetrationController {


    public String cacheMethod1() {

        return "Success";
    }
}
