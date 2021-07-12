package com.company.research_spring.entity.animalDemo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

/**
 * @Author: liwenbo
 * @Date: 2021年07月12日
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class Elephant extends Animal implements AnimalAction {
    private Integer eleAge;

    public Elephant() {
    }

    public Elephant(Integer eleAge) {
        this.eleAge = eleAge;
    }

    public Elephant(String name, String category, Integer eleAge) {
        super(name, category);
        this.eleAge = eleAge;
    }

    /**
     * 叫声
     */
    @Override
    public void voice() {
        System.out.printf("%d岁的%s是%s->呼喊、轰鸣%n", eleAge, super.getName(), super.getCategory());
    }

    /**
     * 打印名称
     */
    @Override
    public void printName() {
        System.out.println(super.getName());
    }
}
