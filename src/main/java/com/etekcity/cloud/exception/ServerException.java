package com.etekcity.cloud.exception;

import com.etekcity.cloud.constant.errorCode.ErrorCode;

/**
 * 服务异常
 *
 * @author Levi
 * @date 2019/7/26/026
 * @since 0.0.1
 */
public class ServerException extends Exception {

    private int code;
    private String msg;
    private Exception exception;

    public ServerException() {
        super();
    }

    public ServerException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public ServerException(ErrorCode errorCode, String msg) {
        this.code = errorCode.getCode();
        this.msg = msg;
    }

    public ServerException(ErrorCode errorCode, String msg, Exception e) {
        this.code = errorCode.getCode();
        this.msg = msg;
        this.exception = e;
    }

    public Exception getException() {
        return exception;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
