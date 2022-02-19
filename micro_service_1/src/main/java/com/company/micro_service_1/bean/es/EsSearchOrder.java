package com.company.micro_service_1.bean.es;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 排序
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsSearchOrder {
    /**
     * 倒序
     */
    public static final String order_by_type__desc = "desc";
    /**
     * 正序
     */
    public static final String order_by_type__asc = "asc";

    private String field;

    private String orderByType = order_by_type__desc;
}
