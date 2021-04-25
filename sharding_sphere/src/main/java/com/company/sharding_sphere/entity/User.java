package com.company.sharding_sphere.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-04-24 20:15:20
 */
@Data
@TableName("t_user")
@ToString
public class User {
    private Long userId;
    private String username;
    private String status;
}
