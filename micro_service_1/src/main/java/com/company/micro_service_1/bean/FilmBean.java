package com.company.micro_service_1.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Date 28/9/2022 0028 下午 1:10
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Data
@Accessors(chain = true)
@TableName("film_bean")
public class FilmBean {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String updateTime;
    private String scope;
    private String type;
    private String magnet;
}
