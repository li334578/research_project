package com.company.micro_service_1.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Date 4/3/2022 0004 上午 11:29
 * @Description cdk bean
 * @Version 1.0.0
 * @Author liwenbo
 */
@Data
@TableName("tab_cdk")
public class Cdk implements Serializable {
    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * cdk信息
     */
    private String cdk;

    /**
     * 活动名称
     */
    private String activeName;

    /**
     * 领用状态  0 未领用 1 已领用
     */
    private Integer useStatus;

    /**
     * 领用时间
     */
    private String useTime;

}
