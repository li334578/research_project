package com.company.research_spring.entity.animalDemo;

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
        super("阿离", "英短");
        this.catAge = 1;
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

    @Override
    public String toString() {
        return "Cat{" +
                "catAge=" + catAge +
                "} " + super.toString();
    }
}
