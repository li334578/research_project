package com.example.micro_service_4.controller;

import com.example.micro_service_4.bean.ClassInfo;
import com.example.micro_service_4.service.ClassInfoService;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date 30/5/2023 0030 下午 2:39
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@RestController
@RequestMapping("/classInfo")
@Slf4j
public class ClassInfoController {

    @Resource
    private ClassInfoService classInfoService;


    @GetMapping("/all")
    public String searchAllClassInfo() {
        classInfoService.list().forEach(System.out::println);
        return "OK";
    }

    @GetMapping("/list")
    public List<ClassInfo> searchListClassInfo() {
        log.info(" searchListClassInfo 被请求了.....");
        return classInfoService.list();
    }

    @GetMapping("/add")
    @Transactional
    public String addClassInfo() {
//        String xid = GlobalTransactionContext.getCurrentOrCreate().getXid();
//        log.info(xid);
        ClassInfo classInfo = new ClassInfo();
        classInfo.setAddress("adda");
        classInfo.setName("刘彻6");
        classInfoService.save(classInfo);
        int a= 1/0;
        return "OK";
    }
}
