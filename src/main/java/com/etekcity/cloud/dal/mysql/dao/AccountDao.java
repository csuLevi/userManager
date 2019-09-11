package com.etekcity.cloud.dal.mysql.dao;

import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import javax.validation.constraints.NotNull;

import com.etekcity.cloud.exception.ServerException;
import com.etekcity.cloud.dal.mysql.model.Account;

/**
 * 账号的数据库操作
 *
 * @author Levi
 * @date 2019/7/26/026
 * @since 0.0.1
 */

public interface AccountDao {
    /**
     * 根据email查找账号
     *
     * @param email email
     * @return 用户账号
     * @throws ServerException 服务异常
     * @throws ServerException 内部异常
     */
    Account findByEmail(@NotNull String email) throws ServerException;

    /**
     * 根据用户ID查找账号
     *
     * @param userId 主键用户ID
     * @return account
     * @throws ServerException 内部异常
     */
    Account findByUserId(long userId) throws ServerException;

    /**
     * 保存用户账号到mysql
     *
     * @param account 用户账号
     * @throws ServerException 服务异常
     */
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    void saveAccount(Account account) throws ServerException;
}
