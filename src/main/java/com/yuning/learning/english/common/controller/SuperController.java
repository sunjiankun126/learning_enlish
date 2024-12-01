package com.yuning.learning.english.common.controller;

import com.yuning.learning.english.common.response.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @package: org.pml.nos.tunnel.framework.controller
 * @Author: Administrator
 * @Date: 2019/5/29 14:47
 * @Version 1.0
 */
public class SuperController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    /**
     * 成功返回
     *
     * @return ApiResponses<Void>
     */
    public ApiResponses<Void> success() {
        int status = HttpStatus.OK.value();
        response.setStatus(status);
        return new ApiResponses<>(status);
    }

    /**
     * 成功返回
     *
     * @param status Http状态
     * @return ApiResponses<Void>
     */
    public ApiResponses<Void> success(HttpStatus status) {
        response.setStatus(status.value());
        return new ApiResponses<>(status.value());
    }

    /**
     * 成功返回
     *
     * @param object 返回对象
     * @return ApiResponses<T>
     */
    public <T> ApiResponses<T> success(T object) {
        int status = HttpStatus.OK.value();
        response.setStatus(status);
        return new ApiResponses<>(status, object);
    }

    /**
     * 成功返回
     *
     * @param status Http状态
     * @param object 返回对象
     * @return ApiResponses<T>
     */
    public <T> ApiResponses<T> success(HttpStatus status, T object) {
        response.setStatus(status.value());
        return new ApiResponses<>(status.value(), object);
    }
}
