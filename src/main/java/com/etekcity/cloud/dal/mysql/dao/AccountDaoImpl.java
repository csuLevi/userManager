package com.etekcity.cloud.dal.mysql.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etekcity.cloud.dal.mysql.repository.AccountRepository;
import com.etekcity.cloud.constant.errorCode.ErrorCode;
import com.etekcity.cloud.exception.ServerException;
import com.etekcity.cloud.dal.mysql.model.Account;

/**
 * mysql数据库的相关操作
 *
 * @author Levi
 * @date 2019/7/26/026
 * @since 0.0.1
 */
@Component
public class AccountDaoImpl implements AccountDao {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account findByEmail(String email) throws ServerException {
        try {
            return accountRepository.findByEmail(email);
        } catch (Exception e) {
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR, "failed to find account by user email", e);
        }
    }

    @Override
    public Account findByUserId(long userId) throws ServerException {
        try {
            return accountRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR, "failed to find account by userId", e);
        }
    }

    @Override
    public void saveAccount(Account account) throws ServerException {
        try {
            accountRepository.save(account);
        } catch (Exception e) {
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR, "failed to save account", e);
        }
    }
}
