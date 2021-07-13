package com.company.research_spring.entity.animalDemo;

import lombok.Data;

/**
 * @Author: liwenbo
 * @Date: 2021年07月12日
 * @Description:
 */
@Data
public abstract class Animal {
    /**
     * 名称
     */
    private String name;
    /**
     * 种类
     */
    private String category;

    public Animal() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Animal(String name, String category) {
        this.name = name;
        this.category = category;
    }
}
