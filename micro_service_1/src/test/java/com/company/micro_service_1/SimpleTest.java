package com.company.micro_service_1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.math.BigDecimal;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description : 简单测试类
 * @date : 2021-11-08 20:12:11
 */
public class SimpleTest {
    @Test
    public void testMethod1() {
        Bean1 bean1 = new Bean1("属性1", 2.0d, 3.0f, 4, 5);
        Bean2 bean2 = new Bean2();
        BeanCopier beanCopier = BeanCopier.create(Bean1.class, Bean2.class, false);
        beanCopier.copy(bean1, bean2, null);
        System.out.println(bean1);
        System.out.println(bean2);
        // 名称相同而类型不同的属性不会被拷贝。 即使源类型是原始类型(int, short和char等)，目标类型是其包装类型(Integer, Short和Character等)，
    }

    @Test
    public void testMethod2() {
        Bean2 bean2 = new Bean2("属性1", 2.0d, 3.0f, 4);
        Bean3 bean3 = new Bean3();
        BeanCopier beanCopier = BeanCopier.create(Bean2.class, Bean3.class, true);
        beanCopier.copy(bean2, bean3, (value, target, context) -> {
            // value 源对象属性，target 目标对象属性类，context 目标对象setter方法名
            if (value instanceof Integer) {
                return value;
            } else if (value instanceof BigDecimal) {
                BigDecimal bd = (BigDecimal) value;
                return bd.toPlainString();
            } else if (value instanceof Double) {
                return String.valueOf(value);
            }
            return value;
        });
        System.out.println(bean2);
        System.out.println(bean3);
    }
}


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
class Bean1 {
    private String properties1;
    private Double properties2;
    private Float properties3;
    private Integer properties4;
    private Integer properties5;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
class Bean2 {
    private String properties1;
    private Double properties2;
    private Float properties3;
    private Integer properties4;
}


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
class Bean3 {
    private String properties1;
    private String properties2;
    private Float properties3;
    private Integer properties4;
}