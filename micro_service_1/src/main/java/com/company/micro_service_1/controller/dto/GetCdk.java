package com.company.micro_service_1.controller.dto;

import lombok.Data;

/**
 * @Date 4/3/2022 0004 下午 1:24
 * @Description 领取cdk
 * @Version 1.0.0
 * @Author liwenbo
 */
@Data
public class GetCdk {

    /**
     * 领用人姓名
     */
    private String name;

    /**
     * 领用活动
     */
    private String activeName;

    /**
     * 领取到的cdk信息
     */
    private String Cdk;

    /**
     * 创建时间
     */
    private String createTime;

}
