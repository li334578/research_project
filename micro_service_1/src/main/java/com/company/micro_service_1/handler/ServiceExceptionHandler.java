package com.company.micro_service_1.handler;

import com.company.micro_service_1.bean.Result;
import com.company.micro_service_1.bean.ResultMsgEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description : 异常处理类
 * @date : 2021-11-26 20:33:55
 */
@RestControllerAdvice
@Slf4j
public class ServiceExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result Exception(Exception e) {
        log.error("未知异常！", e);
        return Result.error(ResultMsgEnum.SERVER_BUSY.getCode(), ResultMsgEnum.SERVER_BUSY.getMessage());
    }
}
