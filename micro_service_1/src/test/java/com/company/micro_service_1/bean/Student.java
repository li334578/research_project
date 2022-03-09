package com.company.micro_service_1.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Date 20/1/2022 下午 3:21
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {
    private Integer id;
    private String name;
    private Integer age;
    private Double score;
    private String className;
    private String remark;
}
