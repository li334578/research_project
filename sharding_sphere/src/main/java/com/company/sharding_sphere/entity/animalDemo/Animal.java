package com.company.sharding_sphere.entity.animalDemo;

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

    public Animal(String name, String category) {
        this.name = name;
        this.category = category;
    }
}
