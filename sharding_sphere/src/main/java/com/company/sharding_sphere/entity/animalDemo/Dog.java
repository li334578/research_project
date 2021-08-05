package com.company.sharding_sphere.entity.animalDemo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

/**
 * @Author: liwenbo
 * @Date: 2021年07月12日
 * @Description: 狗
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class Dog extends Animal implements AnimalAction {

    /**
     * 狗的年龄
     */
    private Integer dogAge;

    public Dog() {
    }

    public Dog(Integer dogAge) {
        this.dogAge = dogAge;
    }

    public Dog(String name, String category, Integer dogAge) {
        super(name, category);
        this.dogAge = dogAge;
    }

    /**
     * 叫声
     */
    @Override
    public void voice() {
        System.out.printf("%d岁的%s是%s->汪汪汪%n", dogAge, super.getName(), super.getCategory());
    }

    /**
     * 打印名称
     */
    @Override
    public void printName() {
        System.out.println(super.getName());
    }
}
