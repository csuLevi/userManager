package com.etekcity.cloud.constant.errorCode;

/**
 * 定义错误码
 *
 * @author Levi
 * @since 0.0.1
 **/
public enum ErrorCode {
    /**
     * 错误码
     */
    SUCCESS(0, "请求成功"),

    INTERNAL_SERVER_ERROR(-10001, "服务器内部错误"),
    INTERNAL_SERVER_BUSY_ERROR(-10002, "服务器繁忙"),
    INTERNAL_SERVER_TIMEOUT_ERROR(-10003, "服务器超时"),

    ACCOUNT_EMAIL_FORMAT_ERROR(-20101, "邮箱不合法"),
    ACCOUNT_PWD_FORMAT_ERROR(-20102, "密码不合法"),
    ACCOUNT_NICKNAME_FORMAT_ERROR(-20103, "昵称不合法"),
    ACCOUNT_ADDRESS_FORMAT_ERROR(-20104, "地址不合法"),
    ACCOUNT_OLD_PWD_FORMAT_ERROR(-20105, "旧密码不合法"),
    ACCOUNT_NEW_PWD_FORMAT_ERROR(-20106, "新密码不合法"),
    ACCOUNT_EMAIL_HASREGISTERED_ERROR(-20111, "邮箱已注册"),
    ACCOUNT_PWD_INCORRECT_ERROR(-20112, "密码不正确"),
    ACCOUNT_EMAIL_NOTEXIST_ERROR(-20113, "邮箱不存在"),

    COMMON_TOKEN_EXPIRED_ERROR(-20201, "用户凭证过期"),
    COMMON_TOKEN_INCORRECT_ERROR(-20202, "用户凭证错误");

    private String msg;
    private int code;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public int getCode() {
        return this.code;
    }

}

