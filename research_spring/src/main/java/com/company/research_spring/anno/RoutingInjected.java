package com.company.research_spring.anno;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author: liwenbo
 * @Date: 2021年09月09日
 * @Description:
 */
@Target({ElementType.FIELD}) // 字段声明
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RoutingInjected {

}
