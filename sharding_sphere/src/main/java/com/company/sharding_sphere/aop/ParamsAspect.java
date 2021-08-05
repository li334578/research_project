package com.company.sharding_sphere.aop;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.company.sharding_sphere.annotation.NonNullCheck;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;

/**
 * @Author: liwenbo
 * @Date: 2021年05月31日
 * @Description: 参数校验aop
 */
@Aspect
@Component
@Order(2)
public class ParamsAspect {

    @Pointcut("execution(* com.company.sharding_sphere.controller..*.*(..))")
    public void paramsPointCut() {

    }

    @Around("paramsPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        StringBuilder resultMsg = new StringBuilder();
        // 接收到请求处理请求参数
        for (Object arg : pjp.getArgs()) {
            for (Field field : arg.getClass().getDeclaredFields()) {
                NonNullCheck nonNullCheck = field.getAnnotation(NonNullCheck.class);
                field.setAccessible(true);
                Object fieldValue = field.get(arg);
                if (Objects.nonNull(nonNullCheck)) {
                    // 注解不为null 值为null
                    if (Objects.isNull(fieldValue)) {
                        resultMsg.append(nonNullCheck.message()).append("|");
                        continue;
                    }
                    // 参数类型为String 值为""
                    if (String.class.isAssignableFrom(field.getType())) {
                        String stringValue = (String) fieldValue;
                        if (Objects.equals(stringValue.trim(), "")) {
                            resultMsg.append(nonNullCheck.message()).append("|");
                            continue;
                        }
                    }
                    // 参数类型为Collection子类
                    if (Collection.class.isAssignableFrom(field.getType())) {
                        Collection collection = (Collection) fieldValue;
                        if (CollectionUtil.isEmpty(collection)) {
                            resultMsg.append(nonNullCheck.message()).append("|");
                        }
                    }
                }

            }
        }
        if (StrUtil.isNotBlank(resultMsg)) {
            // 参数有需要处理的
            return resultMsg.substring(0, resultMsg.length() - 1);
        }
        return pjp.proceed();
    }
}
