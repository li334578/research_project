package com.company.research_spring.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @Author: liwenbo
 * @Date: 2021年09月09日
 * @Description: 我们可以通过该接口中的方法在bean实例化、
 * 配置以及其他初始化方法前后添加一些我们自己的逻辑
 */
@Component
public class StudentPostProcessor implements BeanPostProcessor, Ordered {

    /**
     * 实例化、依赖注入完毕，在调用显示的初始化之前完成一些定制的初始化任务
     * 注意：方法返回值不能为null
     * 如果返回null那么在后续初始化方法将报空指针异常或者通过getBean()方法获取不到bena实例对象
     * 因为后置处理器从Spring IoC容器中取出bean实例对象没有再次放回IoC容器中
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println("初始化 before--实例化的bean对象:" + bean + "\t" + beanName);
        // 可以根据beanName不同执行不同的处理操作
        if (Objects.equals(beanName, "student")) {// 对student bean name进行大写处理
            // 对name属性进行大写处理
            try {
                Field field = bean.getClass().getDeclaredField("name");
                // 设置可以访问
                field.setAccessible(true);
                // 获取bean的相应属性的值
                String original = (String) field.get(bean);
                // 设置bean对应属性的值为大写
                field.set(bean, original.toUpperCase());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // 没有name属性
                e.printStackTrace();
            }
        }
        return bean;
    }

    /**
     * 实例化、依赖注入、初始化完毕时执行 * 注意：方法返回值不能为null
     * 如果返回null那么在后续初始化方法将报空指针异常或者通过getBean()方法获取不到bena实例对象
     * 因为后置处理器从Spring IoC容器中取出bean实例对象没有再次放回IoC容器中
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println("初始化 after...实例化的bean对象:" + bean + "\t" + beanName);
        // 可以根据beanName不同执行不同的处理操作
        return bean;
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
