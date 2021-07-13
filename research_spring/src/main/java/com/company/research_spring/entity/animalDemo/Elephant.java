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
        super("big elephant", "象群");
        this.eleAge = 8;
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

    @Override
    public String toString() {
        return "Elephant{" +
                "eleAge=" + eleAge +
                "} " + super.toString();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getCategory() {
        return super.getCategory();
    }

    @Override
    public void setCategory(String category) {
        super.setCategory(category);
    }
}
