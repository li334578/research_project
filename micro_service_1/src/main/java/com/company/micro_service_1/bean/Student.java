package com.company.micro_service_1.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-10-24 19:56:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private Long id;

    private String name;

    private Integer age;
}
