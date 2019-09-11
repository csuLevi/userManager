package com.etekcity.cloud.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

import com.etekcity.cloud.dto.response.result.VoidResult;
import com.etekcity.cloud.dto.response.Response;
import com.etekcity.cloud.constant.ResponseConstant;
import com.etekcity.cloud.constant.errorCode.ErrorCode;

/**
 * 格式检查工具，包括邮箱格式，密码格式，昵称格式，地址格式
 * 1. 用户邮箱地址，匹配该正则："^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$"
 * 2.密码格式为：6～20位，允许字符范围为 ASCII 表编号33到126的字符
 * 3.昵称格式：昵称长度小于32个字符
 * 4.地址格式： 地址长度小于255个字符
 *
 * @author Levi
 * @date 2019/7/26/026
 * @since 0.0.1
 */
@Slf4j
public class CheckFormatUtil {
    private static final int PASSWORD_MAX_LENGTH = 255;
    /**
     * ^定义了以什么开始; ？表示括号内的内容是可选的;  \.匹配'.';  +一次或多次匹配前面的字符或子表达式
     */
    private static final String EMAIL_FORMAT_REGULAR_EXPRESSION = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private static final String PASSWORD_FORMAT_REGULAR_EXPRESSION = "^[\\u0021-\\u007E]{6,20}$";
    private static final int NICKNAME_MAX_LENGTH = 32;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_FORMAT_REGULAR_EXPRESSION);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_FORMAT_REGULAR_EXPRESSION);

    /**
     * 检查邮箱格式
     *
     * @param email email
     * @return Response
     */
    public static Response checkEmailFormat(String email) {
        VoidResult voidResult = new VoidResult();
        if (email == null) {
            return new Response<>(ErrorCode.ACCOUNT_EMAIL_FORMAT_ERROR, voidResult);
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (matcher.matches()) {
            return ResponseConstant.SUCCESS_RESPONSE;
        }
        return new Response<>(ErrorCode.ACCOUNT_EMAIL_FORMAT_ERROR, voidResult);
    }

    /**
     * 检查密码格式
     *
     * @param pwd password
     * @return Response
     */
    public static Response checkPasswordFormat(String pwd) {
        VoidResult voidResult = new VoidResult();
        if (pwd == null) {
            return new Response<>(ErrorCode.ACCOUNT_PWD_FORMAT_ERROR, voidResult);
        }
        Matcher matcher = PASSWORD_PATTERN.matcher(pwd);
        if (matcher.matches()) {
            return ResponseConstant.SUCCESS_RESPONSE;
        }
        return new Response<>(ErrorCode.ACCOUNT_PWD_FORMAT_ERROR, voidResult);
    }

    /**
     * 检查昵称格式
     *
     * @param nickname nickname
     * @return Response
     */
    public static Response checkNicknameFormat(String nickname) {
        if (NICKNAME_MAX_LENGTH < nickname.length()) {
            return new Response<>(ErrorCode.ACCOUNT_NICKNAME_FORMAT_ERROR, new VoidResult());
        }
        return ResponseConstant.SUCCESS_RESPONSE;
    }

    /**
     * 检查地址格式
     *
     * @param address address
     * @return Response
     */
    public static Response checkAddressFormat(String address) {
        if (PASSWORD_MAX_LENGTH < address.length()) {
            return new Response<>(ErrorCode.ACCOUNT_ADDRESS_FORMAT_ERROR, new VoidResult());
        }
        return ResponseConstant.SUCCESS_RESPONSE;
    }
}
