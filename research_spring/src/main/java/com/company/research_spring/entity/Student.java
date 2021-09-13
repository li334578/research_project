package com.company.research_spring.entity;

import com.company.research_spring.markInterface.AddProperties;
import lombok.Data;

/**
 * @Author: liwenbo
 * @Date: 2021年09月09日
 * @Description:
 */
@Data
public class Student implements AddProperties {
    private String name;
    private Integer age;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
