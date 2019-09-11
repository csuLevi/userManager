package com.etekcity.cloud.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.etekcity.cloud.dto.request.*;
import com.etekcity.cloud.dto.response.Response;
import com.etekcity.cloud.constant.MethodConstant;
import com.etekcity.cloud.dto.response.result.*;
import com.etekcity.cloud.exception.ServerException;
import com.etekcity.cloud.service.UserService;

/**
 * 接入层
 *
 * @author Levi
 * @date 2019/7/25/025
 * @since 0.0.1
 */
@RestController
@RequestMapping(path = MethodConstant.REQUEST_PATH, method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@SuppressWarnings("unchecked")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(path = MethodConstant.REGISTER_METHOD)
    public Response<RegisterResult> userRegister(@RequestBody RegisterRequest request) throws NoSuchAlgorithmException,
            InvalidKeySpecException, ServerException {
        return userService.userRegister(request);
    }

    @RequestMapping(path = MethodConstant.LOGIN_METHOD)
    public Response<LoginResult> userLogin(@RequestBody LoginRequest request) throws NoSuchAlgorithmException,
            InvalidKeySpecException, ServerException {
        return userService.userLogin(request);
    }

    @RequestMapping(path = MethodConstant.GET_USER_INFO_METHOD)
    public Response<GetUserInfoResult> getUserInfo(@RequestHeader(MethodConstant.AUTHORIZATION_REQUEST_HEADER) String request)
            throws ServerException {
        return userService.getUserInfo(request);
    }

    @RequestMapping(path = MethodConstant.UPDATE_USER_INFO_METHOD)
    public Response<UpdateUserInfoResult> updateUserInfo(@RequestHeader(MethodConstant.AUTHORIZATION_REQUEST_HEADER) String requestHeader,
                                                         @RequestBody UpdateInfoRequest requestBody) throws ServerException {
        return userService.updateUserInfo(requestHeader, requestBody);
    }

    @RequestMapping(path = MethodConstant.UPDATE_PASSWORD_METHOD)
    public Response<UpdatePasswordResult> updatePassword(@RequestHeader(MethodConstant.AUTHORIZATION_REQUEST_HEADER) String requestHeader,
                                                         @RequestBody UpdatePasswordRequest requestBody)
            throws NoSuchAlgorithmException, InvalidKeySpecException, ServerException {
        return userService.updatePassword(requestHeader, requestBody);
    }

    @RequestMapping(path = MethodConstant.LOGOUT_METHOD)
    public Response<LogoutResult> userLogout(@RequestHeader(MethodConstant.AUTHORIZATION_REQUEST_HEADER) String request)
            throws ServerException {
        return userService.userLogout(request);
    }
}
