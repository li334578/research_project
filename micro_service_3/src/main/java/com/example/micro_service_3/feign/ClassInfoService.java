package com.example.micro_service_3.feign;

import com.example.micro_service_3.bean.ClassInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Date 30/5/2023 0030 下午 2:55
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@FeignClient(value = "micro-service-4")
@Service
public interface ClassInfoService {

    @GetMapping("/classInfo/all")
    String searchAllClassInfo();

    @GetMapping("/classInfo/list")
    List<ClassInfo> searchListClassInfo();

    @GetMapping("/classInfo/add")
    String addClassInfo();
}
