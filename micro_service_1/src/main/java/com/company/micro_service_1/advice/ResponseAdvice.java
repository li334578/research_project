package com.company.micro_service_1.advice;

import com.company.micro_service_1.bean.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description : 用于统一封装controller中接口的返回结果
 * @date : 2021-11-26 20:30:51
 */
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 是否开启功能 true：是
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 处理返回结果
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //处理字符串类型数据
        if (o instanceof String) {
            try {
                return objectMapper.writeValueAsString(Result.success(o));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        //返回类型是否已经封装
        if (o instanceof Result) {
            return o;
        }
        return Result.success(o);
    }
}
