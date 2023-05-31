package com.example.micro_service_4.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Date 30/5/2023 0030 下午 2:34
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Data
@TableName("class_info")
public class ClassInfo {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String address;
}
