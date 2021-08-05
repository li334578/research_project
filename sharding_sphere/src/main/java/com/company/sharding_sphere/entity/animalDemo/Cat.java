package com.company.sharding_sphere.entity.animalDemo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

/**
 * @Author: liwenbo
 * @Date: 2021年07月12日
 * @Description: 猫
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class Cat extends Animal implements AnimalAction {

    private Integer catAge;

    public Cat() {
    }

    public Cat(Integer catAge) {
        this.catAge = catAge;
    }

    public Cat(String name, String category, Integer catAge) {
        super(name, category);
        this.catAge = catAge;
    }

    /**
     * 叫声
     */
    @Override
    public void voice() {
        System.out.printf("%d岁的%s是%s->喵喵喵%n", catAge, super.getName(), super.getCategory());
    }

    /**
     * 打印名称
     */
    @Override
    public void printName() {
        System.out.println(super.getName());
    }
}
