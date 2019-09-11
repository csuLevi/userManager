package com.etekcity.cloud.service.impl.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etekcity.cloud.dto.response.result.LogoutResult;
import com.etekcity.cloud.service.impl.TokenService;
import com.etekcity.cloud.util.StrUtil;
import com.etekcity.cloud.dto.response.Response;
import com.etekcity.cloud.constant.errorCode.ErrorCode;
import com.etekcity.cloud.exception.ServerException;

/**
 * 用户登出处理器
 *
 * @author Levi
 * @date 2019/7/29/029
 * @since 0.0.1
 */
@Component
public class UserLogoutHandler {
    @Autowired
    private TokenService tokenService;

    public Response<LogoutResult> userLogout(String request) throws ServerException {
        String[] requestList = StrUtil.split(request, StrUtil.SPLIT_CHAR_SPACE);
        String token = requestList[1];
        long userId = Long.valueOf(requestList[0]);
        int validateTokenResult = tokenService.validateToken(userId, token);
        if (validateTokenResult == ErrorCode.COMMON_TOKEN_INCORRECT_ERROR.getCode()) {
            return new Response<>(ErrorCode.COMMON_TOKEN_INCORRECT_ERROR, new LogoutResult());
        }
        if (validateTokenResult == ErrorCode.COMMON_TOKEN_EXPIRED_ERROR.getCode()) {
            return new Response<>(ErrorCode.COMMON_TOKEN_INCORRECT_ERROR, new LogoutResult());
        }
        tokenService.deleteOneToken(userId, token);
        return new Response<>(ErrorCode.SUCCESS, new LogoutResult());
    }
}
