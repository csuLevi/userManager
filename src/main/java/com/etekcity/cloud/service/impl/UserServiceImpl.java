package com.etekcity.cloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.etekcity.cloud.constant.ResponseConstant;
import com.etekcity.cloud.dto.request.*;
import com.etekcity.cloud.exception.ServerException;
import com.etekcity.cloud.service.UserService;
import com.etekcity.cloud.service.impl.handler.*;
import com.etekcity.cloud.dto.response.Response;

/**
 * 用户管理
 *
 * @author Levi
 * @date 2019/7/26/026
 * @since 0.0.1
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRegisterHandler registerHandler;
    @Autowired
    private UserLoginHandler userLoginHandler;
    @Autowired
    private UserLogoutHandler userLogoutHandler;
    @Autowired
    private GetUserInfoHandler getUserInfoHandler;
    @Autowired
    private UpdateUserInfoHandler updateUserInfoHandler;
    @Autowired
    private UpdatePasswordHandler updatePasswordHandler;

    @Override
    public Response userRegister(RegisterRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException,
            ServerException {
        return registerHandler.userRegisterHandler(request);
    }

    @Override
    public Response userLogin(LoginRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException,
            ServerException {
        return userLoginHandler.userLogin(request);
    }

    @Override
    public Response userLogout(String request) throws ServerException {
        return userLogoutHandler.userLogout(request);
    }

    @Override
    public Response getUserInfo(String request) throws ServerException {
        return getUserInfoHandler.getUserInfo(request);
    }

    @Override
    public Response updateUserInfo(String requestHeader, UpdateInfoRequest requestBody) throws ServerException {
        return updateUserInfoHandler.updateUserInfo(requestHeader, requestBody);
    }

    @Override
    public Response updatePassword(String requestHeader, UpdatePasswordRequest requestBody)
            throws NoSuchAlgorithmException, InvalidKeySpecException, ServerException {
        return updatePasswordHandler.updatePassword(requestHeader, requestBody);
    }
}
