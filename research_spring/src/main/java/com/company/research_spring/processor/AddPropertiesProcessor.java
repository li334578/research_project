package com.company.research_spring.processor;

import com.company.research_spring.markInterface.AddProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-09-12 09:46:39
 */
@Component
public class AddPropertiesProcessor implements BeanPostProcessor, Ordered {
    @Override
    public int getOrder() {
        return 3;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (bean instanceof AddProperties) {
            // bean实现了该接口
            Field[] declaredFields = beanClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                System.out.println(declaredField);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
