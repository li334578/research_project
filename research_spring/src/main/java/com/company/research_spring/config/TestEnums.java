package com.company.research_spring.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * @Author: liwenbo
 * @Date: 2021年09月08日
 * @Description:
 */
public enum TestEnums {

    Instance;

    private String name;

    TestEnums() {
    }

    @Value("${spring.boot.name}")
    public String getName() {
        return name;
    }

    @Value("${spring.boot.name}")
    public void setName(String name) {
        System.out.println("setter method ....");
        this.name = name;
    }
}
