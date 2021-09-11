package com.company.research_spring.anno;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author: liwenbo
 * @Date: 2021年09月09日
 * @Description:
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RoutingSwitch {
    String value() default "";
}
