package com.example.micro_service_5_zk.controller

import com.example.micro_service_5_zk.service.HelloService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

/**
 * @Date 2023-09-11 14:41
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@RestController
@RequestMapping("/kotlin/")
class KotlinController(val helloService: HelloService) {


    @GetMapping("/hello")
    fun hello(): String {
        println(helloService.hello)
        return "Hello  Kotlin"
    }
}