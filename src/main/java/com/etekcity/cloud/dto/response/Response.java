package com.etekcity.cloud.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import com.etekcity.cloud.constant.errorCode.ErrorCode;


/**
 * 定义响应体
 *
 * @author Levi
 * @date 2019/7/25/025
 * @since 0.0.1
 */
@Data
public class Response<T> {

    private int code;

    private String msg;

    private T result;

    public Response(ErrorCode errorCode, T result) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.result = result;
    }

    public Response(int code) {
        this.code = code;
    }
}
