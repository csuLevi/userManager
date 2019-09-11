package com.etekcity.cloud.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.etekcity.cloud.dto.request.*;
import com.etekcity.cloud.dto.response.Response;
import com.etekcity.cloud.exception.ServerException;

/**
 * 用户注册接口
 *
 * @author Levi
 * @date 2019/7/25/025
 * @since 0.0.1
 */
public interface UserService {
    /**
     * 用户注册
     *
     * @param request 请求体
     * @return response
     * @throws NoSuchAlgorithmException 校验密码异常
     * @throws InvalidKeySpecException  校验密码异常
     * @throws ServerException          服务异常
     */
    Response userRegister(RegisterRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException, ServerException;

    /**
     * 用户登录
     *
     * @param request 请求体
     * @return response
     * @throws NoSuchAlgorithmException 校验密码异常
     * @throws InvalidKeySpecException  校验密码异常
     * @throws ServerException          服务异常
     */
    Response userLogin(LoginRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException, ServerException;

    /**
     * 用户登出
     *
     * @param request 请求体
     * @return response
     * @throws ServerException 服务异常
     */
    Response userLogout(String request) throws ServerException;

    /**
     * 获取用户信息
     *
     * @param request 请求体
     * @return response
     * @throws ServerException 服务异常
     */
    Response getUserInfo(String request) throws ServerException;

    /**
     * 更新用户信息
     *
     * @param requestHeader 请求头
     * @param requestBody   请求体
     * @return response
     * @throws ServerException 服务异常
     */
    Response updateUserInfo(String requestHeader, UpdateInfoRequest requestBody) throws ServerException;

    /**
     * 更新登录密码
     *
     * @param requestHeader 请求头
     * @param requestBody   请求体
     * @return response
     * @throws NoSuchAlgorithmException 校验密码异常
     * @throws InvalidKeySpecException  校验密码异常
     * @throws ServerException          服务异常
     */
    Response updatePassword(String requestHeader, UpdatePasswordRequest requestBody)
            throws NoSuchAlgorithmException, InvalidKeySpecException, ServerException;
}
