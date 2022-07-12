package com.company.micro_service_1.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (Product)实体类
 *
 * @author makejava
 * @since 2022-07-09 11:25:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Product implements Serializable {
    private static final long serialVersionUID = 988595931279254237L;
    /**
     * id 雪花id
     */
    private Long id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品数量
     */
    private Integer count;
    /**
     * 商品备注
     */
    private String remark;

}

