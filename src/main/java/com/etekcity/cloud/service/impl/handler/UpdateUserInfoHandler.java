package com.etekcity.cloud.service.impl.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etekcity.cloud.dal.mysql.dao.AccountDao;
import com.etekcity.cloud.service.impl.TokenService;
import com.etekcity.cloud.util.CheckFormatUtil;
import com.etekcity.cloud.util.DateUtil;
import com.etekcity.cloud.util.StrUtil;
import com.etekcity.cloud.dto.response.Response;
import com.etekcity.cloud.constant.errorCode.ErrorCode;
import com.etekcity.cloud.dto.request.UpdateInfoRequest;
import com.etekcity.cloud.dto.response.result.UpdateUserInfoResult;
import com.etekcity.cloud.exception.ServerException;
import com.etekcity.cloud.dal.mysql.model.Account;

/**
 * 更新用户信息
 *
 * @author Levi
 * @date 2019/7/29/029
 * @since 0.0.1
 */
@Component
public class UpdateUserInfoHandler {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AccountDao accountDao;

    public Response<UpdateUserInfoResult> updateUserInfo(String requestHeader, UpdateInfoRequest requestBody)
            throws ServerException {
        String[] requestList = StrUtil.split(requestHeader, StrUtil.SPLIT_CHAR_SPACE);
        String token = requestList[1];
        long userId = Long.valueOf(requestList[0]);
        String nickname = requestBody.getNickname();
        String address = requestBody.getAddress();
        int validateTokenResult = tokenService.validateToken(userId, token);
        if (validateTokenResult == ErrorCode.COMMON_TOKEN_INCORRECT_ERROR.getCode()) {
            return new Response<>(ErrorCode.COMMON_TOKEN_INCORRECT_ERROR, new UpdateUserInfoResult());
        }
        if (validateTokenResult == ErrorCode.COMMON_TOKEN_EXPIRED_ERROR.getCode()) {
            return new Response<>(ErrorCode.COMMON_TOKEN_EXPIRED_ERROR, new UpdateUserInfoResult());
        }
        Response checkNicknameResponse = CheckFormatUtil.checkNicknameFormat(nickname);
        if (ErrorCode.SUCCESS.getCode() != checkNicknameResponse.getCode()) {
            return new Response<>(ErrorCode.ACCOUNT_NICKNAME_FORMAT_ERROR, new UpdateUserInfoResult());
        }

        Response checkAddressResponse = CheckFormatUtil.checkAddressFormat(address);
        if (ErrorCode.SUCCESS.getCode() != checkAddressResponse.getCode()) {
            return new Response<>(ErrorCode.ACCOUNT_ADDRESS_FORMAT_ERROR, new UpdateUserInfoResult());
        }
        Account account = accountDao.findByUserId(userId);
        account.setUpdateTime(DateUtil.getTimeStamp());
        account.setNickname(nickname);
        account.setAddress(address);
        accountDao.saveAccount(account);
        return new Response<>(ErrorCode.SUCCESS, new UpdateUserInfoResult());
    }
}
