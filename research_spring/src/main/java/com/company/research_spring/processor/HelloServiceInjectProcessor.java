package com.company.research_spring.processor;

import com.company.research_spring.anno.RoutingInjected;
import com.company.research_spring.proxy.RoutingBeanProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * @Author: liwenbo
 * @Date: 2021年09月09日
 * @Description:
 */
@Component
public class HelloServiceInjectProcessor implements BeanPostProcessor, Ordered {


    @Resource
    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = bean.getClass();
        Field[] targetField = targetClass.getDeclaredFields();
        for (Field field : targetField) {
            // 找到注解
            if (field.isAnnotationPresent(RoutingInjected.class)) {
                // 不是接口 报错
                if (!field.getType().isInterface()) {
                    throw new RuntimeException("RoutingInjected field must be declared as an interface:" + field.getName()
                            + " @Class " + targetClass.getName());
                }
                try {
                    handleRoutingInjected(field, bean, field.getType());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }

    private void handleRoutingInjected(Field field, Object bean, Class type) throws IllegalAccessException {
        // candidates 候选人
        Map<String, Object> candidates = applicationContext.getBeansOfType(type);
        field.setAccessible(true);
        if (candidates.size() == 1) {
            field.set(bean, candidates.values().iterator().next());
        } else if (candidates.size() == 2) {
            // 创建该接口的代理类
            Object proxy = RoutingBeanProxyFactory.createProxy(type, candidates);
            field.set(bean, proxy);
        } else {
            throw new IllegalArgumentException("Find more than 2 beans for type: " + type);
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
