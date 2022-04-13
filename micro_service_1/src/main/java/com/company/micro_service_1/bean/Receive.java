package com.company.micro_service_1.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Date 4/3/2022 0004 下午 2:17
 * @Description 领取信息表
 * @Version 1.0.0
 * @Author liwenbo
 */
@Data
@TableName("tab_receive")
public class Receive {

    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String userName;

    /**
     * 活动名称
     */
    private String activeName;

    /**
     * cdk信息
     */
    private String cdk;

    /**
     * 领取时间
     */
    private String receiveTime;

}
