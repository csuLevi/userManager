package com.etekcity.cloud.dto.response.result;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import com.etekcity.cloud.dal.mysql.model.Account;

/**
 * 用户登录返回的结果
 *
 * @author Levi
 * @date 2019/7/29/029
 * @since 0.0.1
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class LoginResult {
    private String email;
    private String nickname;
    private String address;
    private String token;
    private Long userId;
    private Integer expiredIn;
    private Date createAt;
    private Date updateAt;

    public void setLoginResult(Account account) {
        this.email = account.getEmail();
        this.nickname = account.getNickname();
        this.address = account.getAddress();
        this.userId = account.getUserId();
        this.createAt = account.getCreateTime();
        this.updateAt = account.getUpdateTime();
    }
}
