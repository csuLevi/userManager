package com.etekcity.cloud.service.impl.handler;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etekcity.cloud.dal.mysql.dao.AccountDao;
import com.etekcity.cloud.exception.ServerException;
import com.etekcity.cloud.service.impl.TokenService;
import com.etekcity.cloud.util.CheckFormatUtil;
import com.etekcity.cloud.util.DateUtil;
import com.etekcity.cloud.util.EncryptPasswordUtil;
import com.etekcity.cloud.util.StrUtil;
import com.etekcity.cloud.dto.response.Response;
import com.etekcity.cloud.constant.errorCode.ErrorCode;
import com.etekcity.cloud.dto.request.UpdatePasswordRequest;
import com.etekcity.cloud.dto.response.result.UpdatePasswordResult;
import com.etekcity.cloud.dal.mysql.model.Account;

/**
 * 用户密码修改处理
 *
 * @author Levi
 * @date 2019/7/29/029
 * @since 0.0.1
 */
@Component
public class UpdatePasswordHandler {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AccountDao accountDao;

    public Response<UpdatePasswordResult> updatePassword(String requestHeader, UpdatePasswordRequest requestBody)
            throws NoSuchAlgorithmException, InvalidKeySpecException, ServerException {
        String[] requestList = StrUtil.split(requestHeader, StrUtil.SPLIT_CHAR_SPACE);
        long userId = Long.valueOf(requestList[0]);
        String token = requestList[1];
        String oldPassword = requestBody.getOldPassword();
        String newPassword = requestBody.getNewPassword();
        Response checkOldPasswordResponse = CheckFormatUtil.checkPasswordFormat(oldPassword);
        if (ErrorCode.SUCCESS.getCode() != checkOldPasswordResponse.getCode()) {
            return new Response<>(ErrorCode.ACCOUNT_OLD_PWD_FORMAT_ERROR, new UpdatePasswordResult());
        }
        Response checkNewPasswordResponse = CheckFormatUtil.checkPasswordFormat(newPassword);
        if (ErrorCode.SUCCESS.getCode() != checkNewPasswordResponse.getCode()) {
            return new Response<>(ErrorCode.ACCOUNT_NEW_PWD_FORMAT_ERROR, new UpdatePasswordResult());
        }
        int validateTokenResult = tokenService.validateToken(userId, token);
        if (validateTokenResult == ErrorCode.COMMON_TOKEN_INCORRECT_ERROR.getCode()) {
            return new Response<>(ErrorCode.COMMON_TOKEN_INCORRECT_ERROR, new UpdatePasswordResult());
        }
        if (validateTokenResult == ErrorCode.COMMON_TOKEN_EXPIRED_ERROR.getCode()) {
            return new Response<>(ErrorCode.COMMON_TOKEN_EXPIRED_ERROR, new UpdatePasswordResult());
        }
        Account account = accountDao.findByUserId(userId);
        boolean validatePassword = EncryptPasswordUtil.validatePassword(oldPassword, account.getPassword());
        if (!validatePassword) {
            return new Response<>(ErrorCode.ACCOUNT_PWD_FORMAT_ERROR, new UpdatePasswordResult());
        }
        account.setPassword(EncryptPasswordUtil.encryptPassword(newPassword));
        tokenService.deleteAllToken(userId);
        account.setUpdateTime(DateUtil.getTimeStamp());
        accountDao.saveAccount(account);
        return new Response<>(ErrorCode.SUCCESS, new UpdatePasswordResult());
    }
}
