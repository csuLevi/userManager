package com.etekcity.cloud.service.impl.handler;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etekcity.cloud.dal.mysql.dao.AccountDao;
import com.etekcity.cloud.dto.request.LoginRequest;
import com.etekcity.cloud.dto.response.result.VoidResult;
import com.etekcity.cloud.service.impl.TokenService;
import com.etekcity.cloud.util.CheckFormatUtil;
import com.etekcity.cloud.util.EncryptPasswordUtil;
import com.etekcity.cloud.dto.response.Response;
import com.etekcity.cloud.constant.errorCode.ErrorCode;
import com.etekcity.cloud.dto.response.result.LoginResult;
import com.etekcity.cloud.exception.ServerException;
import com.etekcity.cloud.dal.mysql.model.Account;

/**
 * 用户登录业务
 *
 * @author Levi
 * @date 2019/7/26/026
 * @since 0.0.1
 */
@Component
public class UserLoginHandler {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private TokenService tokenService;

    public Response<LoginResult> userLogin(LoginRequest request) throws NoSuchAlgorithmException,
            InvalidKeySpecException, ServerException {
        String email = request.getEmail();
        String password = request.getPassword();
        Response checkEmailResponse = CheckFormatUtil.checkEmailFormat(email);
        int checkEmailCode = checkEmailResponse.getCode();
        if (ErrorCode.SUCCESS.getCode() != checkEmailCode) {
            return new Response<>(ErrorCode.ACCOUNT_EMAIL_FORMAT_ERROR, new LoginResult());
        }
        Response checkPwdResponse = CheckFormatUtil.checkPasswordFormat(password);
        if (ErrorCode.SUCCESS.getCode() != checkPwdResponse.getCode()) {
            return new Response<>(ErrorCode.ACCOUNT_PWD_FORMAT_ERROR, new LoginResult());
        }
        Account account = accountDao.findByEmail(email);
        if (null == account) {
            return new Response<>(ErrorCode.ACCOUNT_EMAIL_NOTEXIST_ERROR, new LoginResult());
        }
        boolean validatePassword = EncryptPasswordUtil.validatePassword(password, account.getPassword());
        if (!validatePassword) {
            return new Response<>(ErrorCode.ACCOUNT_PWD_INCORRECT_ERROR, new LoginResult());
        }
        String token = TokenService.generateToken();
        tokenService.saveToken(account.getUserId(), token);
        LoginResult loginResult = new LoginResult();
        loginResult.setLoginResult(account);
        loginResult.setExpiredIn(TokenService.getExpiresIn());
        loginResult.setToken(token);
        return new Response<>(ErrorCode.SUCCESS, loginResult);
    }
}
