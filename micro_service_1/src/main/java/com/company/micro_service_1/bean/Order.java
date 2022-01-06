package com.company.micro_service_1.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-12-01 20:18:06
 */
@Data
public class Order {
    private Long id;
    private Date createTime;
}
