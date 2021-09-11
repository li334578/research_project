package com.company.research_spring.entity;

import lombok.Data;

/**
 * @Author: liwenbo
 * @Date: 2021年09月09日
 * @Description:
 */
@Data
public class Student {
    private String name;
    private Integer age;

    void init() {
        System.out.println("initial method launch ...");
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
