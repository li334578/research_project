package com.company.micro_service_1.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date 18/1/2022 下午 1:59
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String name;
    private Integer age;
    private String remark;
}
