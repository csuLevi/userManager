package com.etekcity.cloud.dal.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etekcity.cloud.dal.mysql.model.Account;

/**
 * 使用JpaRepository执行mysql操作
 *
 * @author Levi
 * @date 2019/7/26/026
 * @since 0.0.1
 */

public interface AccountRepository extends JpaRepository<Account, Integer> {
    /**
     * 根据email查找账号
     *
     * @param email email
     * @return 用户账号
     */
    Account findByEmail(String email);

    /**
     * 根据用户ID查找账号
     *
     * @param userId 主键用户ID
     * @return 用户账号
     */
    Account findByUserId(long userId);

}
