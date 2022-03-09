package com.company.micro_service_1.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date 19/1/2022 下午 1:28
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private String title;
    private String alt;
    private String img;
    private String price;
}
