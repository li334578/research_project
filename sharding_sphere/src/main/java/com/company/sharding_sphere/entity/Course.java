package com.company.sharding_sphere.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description : 课程信息
 * @date : 2021-04-22 21:10:16
 */
@Data
@ToString
public class Course {
    private Long cid;
    private String cname;
    private Long userId;
    private String status;
}
