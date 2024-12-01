package com.yuning.learning.english.common.response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangfei
 * @version 1.0
 * @date 2022/7/7 17:19
 */
@Api
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    @ApiModelProperty(value = "消息码", dataType = "Integer", example = "0")
    private Integer code;
    /**
     * 响应提示信息
     */
    @ApiModelProperty(value = "返回消息", dataType = "String", example = "success")
    private String message;
    /**
     * 响应结果对象
     */
    @ApiModelProperty(value = "具体数据", dataType = "Object", example = "null")
    private T data;
}
