package com.etekcity.cloud.dto.response.result;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Value;

import com.etekcity.cloud.dal.mysql.model.Account;

/**
 * 获取用户信息的返回结果
 *
 * @author Levi
 * @date 2019/7/30/030
 * @since 0.0.1
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class GetUserInfoResult {
    private Long userId;
    private String email;
    private String nickname;
    private String address;
    private Date createAt;
    private Date updateAt;

    public GetUserInfoResult() {
    }

    public GetUserInfoResult(Account account) {
        this.userId = account.getUserId();
        this.email = account.getEmail();
        this.nickname = account.getNickname();
        this.address = account.getAddress();
        this.createAt = account.getCreateTime();
        this.updateAt = account.getUpdateTime();
    }
}
