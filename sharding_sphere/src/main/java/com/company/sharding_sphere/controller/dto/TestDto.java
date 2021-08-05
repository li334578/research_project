package com.company.sharding_sphere.controller.dto;

import com.company.sharding_sphere.annotation.NonNullCheck;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: liwenbo
 * @Date: 2021年05月31日
 * @Description:
 */
@Data
@ToString
public class TestDto {
    @NonNullCheck(message = "param不能为null")
    private String paramStr;
}
