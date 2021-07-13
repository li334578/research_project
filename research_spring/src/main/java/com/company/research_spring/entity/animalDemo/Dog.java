package com.company.research_spring.entity.animalDemo;

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
        super("大黄", "斗牛");
        this.dogAge = 2;
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
        return "Dog{" +
                "dogAge=" + dogAge +
                "} " + super.toString();
    }
}
