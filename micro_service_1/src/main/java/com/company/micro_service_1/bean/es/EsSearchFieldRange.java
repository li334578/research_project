package com.company.micro_service_1.bean.es;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 范围搜索
 *
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsSearchFieldRange {
    /**
     * 列名称
     */
    private String field;
    /**
     * 最大值
     */
    private Long maxValue;
    /**
     * 最小值
     */
    private Long minValue;
    /**
     * 列映射
     */
    private EsFieldType esFieldType;
    /**
     * 是否包含边界值 默认不包含
     */
    private Boolean includeBoundaryValues;

    public EsSearchFieldRange(String field, Long maxValue, Long minValue, EsFieldType esFieldType) {
        this.field = field;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.esFieldType = esFieldType;
        this.includeBoundaryValues = false;
    }
}
