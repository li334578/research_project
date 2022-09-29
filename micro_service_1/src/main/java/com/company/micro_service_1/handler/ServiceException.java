package com.company.micro_service_1.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.checkerframework.common.value.qual.ArrayLen;

/**
 * @Date 26/9/2022 0026 下午 2:06
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class ServiceException extends RuntimeException {
    private Long errCode;
    private String errMsg;
    private Object errorData;
    private Throwable e;
}
