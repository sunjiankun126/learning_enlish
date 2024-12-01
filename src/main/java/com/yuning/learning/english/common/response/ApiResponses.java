/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.yuning.learning.english.common.response;


import com.yuning.learning.english.common.utils.ResponseUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * GET: 200 OK
 * POST: 201 Created
 * PUT: 200 OK
 * PATCH: 200 OK
 * DELETE: 204 No Content
 * 接口返回(多态)
 *
 * @Author: Administrator
 * @Date: 2019/5/29 14:47
 */
@Getter
public class ApiResponses<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "HTTP 状态码", example = "200")
    private Integer status;

    @ApiModelProperty(value = "返回对象")
    private T result;

    public ApiResponses() {

    }

    public ApiResponses(Integer status) {
        this.status = status;
    }

    public ApiResponses(T result) {
        this.result = result;
    }

    public ApiResponses(Integer status, T result) {
        this.status = status;
        this.result = result;
    }

    /**
     * TODO(likun) 此方法已废弃，等新方法稳定后删掉
     *
     * 不需要返回结果
     *
     * @param status
     */
    @Deprecated
    public static ApiResponses<Void> success(HttpServletResponse response, HttpStatus status) {
        response.setStatus(status.value());
        return SuccessResponses.<Void>builder().status(status.value()).build();

    }

    /**
     * TODO(likun) 此方法已废弃，等新方法稳定后删掉
     *
     * 成功返回
     *
     * @param object
     */
    @Deprecated
    public static <T> ApiResponses<T> success(HttpServletResponse response, T object) {
        return success(response, HttpStatus.OK, object);

    }

    /**
     * TODO(likun) 此方法已废弃，等新方法稳定后删掉
     *
     * 成功返回
     *
     * @param status
     * @param object
     */
    @Deprecated
    public static <T> ApiResponses<T> success(HttpServletResponse response, HttpStatus status, T object) {
        response.setStatus(status.value());
        return SuccessResponses.<T>builder().status(status.value()).result(object).build();

    }

    /**
     * 失败返回
     *
     * @param errorCode
     * @param exception
     */
    public static <T> ApiResponses<T> failure(ErrorCode errorCode, Exception exception) {
        return ResponseUtils.exceptionMsg(FailedResponse.builder().msg(errorCode.getMsg()).error(errorCode.getError())
                .show(errorCode.isShow())
                .time(LocalDateTime.now())
                .status(errorCode.getHttpCode())
                .build(), exception);
    }

}
