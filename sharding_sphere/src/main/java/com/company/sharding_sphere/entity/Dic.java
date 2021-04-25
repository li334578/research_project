package com.company.sharding_sphere.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-04-24 20:42:57
 */
@Data
@NoArgsConstructor
public class Dic {
    private Long dicId;
    private String dicValue;
    private Integer dicStatus;

    public Dic(String dicValue, Integer dicStatus) {
        this.dicValue = dicValue;
        this.dicStatus = dicStatus;
    }
}
