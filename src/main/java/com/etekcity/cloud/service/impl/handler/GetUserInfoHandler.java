package com.etekcity.cloud.service.impl.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etekcity.cloud.dal.mysql.dao.AccountDao;
import com.etekcity.cloud.service.impl.TokenService;
import com.etekcity.cloud.util.StrUtil;
import com.etekcity.cloud.dto.response.Response;
import com.etekcity.cloud.constant.errorCode.ErrorCode;
import com.etekcity.cloud.dto.response.result.GetUserInfoResult;
import com.etekcity.cloud.exception.ServerException;
import com.etekcity.cloud.dal.mysql.model.Account;

/**
 * 获取用户信息
 *
 * @author Levi
 * @date 2019/7/26/026
 * @since 0.0.1
 */
@Component
public class GetUserInfoHandler {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AccountDao accountDao;


    public Response<GetUserInfoResult> getUserInfo(String request) throws ServerException {
        String[] requestList = StrUtil.split(request, StrUtil.SPLIT_CHAR_SPACE);
        long userId = Long.valueOf(requestList[0]);
        String token = requestList[1];
        int validateTokenResult = tokenService.validateToken(userId, token);
        if (validateTokenResult == ErrorCode.COMMON_TOKEN_INCORRECT_ERROR.getCode()) {
            return new Response<>(ErrorCode.COMMON_TOKEN_INCORRECT_ERROR, new GetUserInfoResult());
        }
        if (validateTokenResult == ErrorCode.COMMON_TOKEN_EXPIRED_ERROR.getCode()) {
            return new Response<>(ErrorCode.COMMON_TOKEN_EXPIRED_ERROR, new GetUserInfoResult());
        }
        Account account;
        account = accountDao.findByUserId(userId);
        if (account == null) {
            return new Response<>(ErrorCode.ACCOUNT_EMAIL_NOTEXIST_ERROR, new GetUserInfoResult());
        }
        GetUserInfoResult getUserInfoResult = new GetUserInfoResult(account);
        return new Response<>(ErrorCode.SUCCESS, getUserInfoResult);
    }
}
