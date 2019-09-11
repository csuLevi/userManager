package com.etekcity.cloud.service.impl.handler;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etekcity.cloud.dal.mysql.dao.AccountDao;
import com.etekcity.cloud.dto.request.RegisterRequest;
import com.etekcity.cloud.util.CheckFormatUtil;
import com.etekcity.cloud.util.DateUtil;
import com.etekcity.cloud.util.EncryptPasswordUtil;
import com.etekcity.cloud.dto.response.result.RegisterResult;
import com.etekcity.cloud.dto.response.Response;
import com.etekcity.cloud.constant.errorCode.ErrorCode;
import com.etekcity.cloud.exception.ServerException;
import com.etekcity.cloud.dal.mysql.model.Account;

/**
 * 用户注册
 *
 * @author Levi
 * @date 2019/7/26/026
 * @since 0.0.1
 */
@Component
public class UserRegisterHandler {
    @Autowired
    private AccountDao accountDao;

    public Response<RegisterResult> userRegisterHandler(RegisterRequest request) throws NoSuchAlgorithmException,
            InvalidKeySpecException, ServerException {
        String email = request.getEmail();
        String password = request.getPassword();
        Response checkEmailResponse = CheckFormatUtil.checkEmailFormat(email);
        if (ErrorCode.SUCCESS.getCode() != checkEmailResponse.getCode()) {
            return new Response<>(ErrorCode.ACCOUNT_EMAIL_FORMAT_ERROR, new RegisterResult());
        }
        Response checkPwdResponse = CheckFormatUtil.checkPasswordFormat(password);
        if (ErrorCode.SUCCESS.getCode() != checkPwdResponse.getCode()) {
            return new Response<>(ErrorCode.ACCOUNT_PWD_INCORRECT_ERROR, new RegisterResult());
        }
        RegisterResult registerResult = new RegisterResult();
        synchronized (this) {
            Account accountInDb = accountDao.findByEmail(email);
            if (null != accountInDb) {
                return new Response<>(ErrorCode.ACCOUNT_EMAIL_HASREGISTERED_ERROR, new RegisterResult());
            }
            Account account = new Account();
            account.setEmail(email);
            account.setPassword(EncryptPasswordUtil.encryptPassword(password));
            account.setCreateTime(DateUtil.getTimeStamp());
            accountDao.saveAccount(account);
            registerResult.setUserId(account.getUserId());
            registerResult.setCreatAt(DateUtil.getTimeStamp());
        }
        return new Response<>(ErrorCode.SUCCESS, registerResult);
    }
}
