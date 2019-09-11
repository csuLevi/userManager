package com.etekcity.cloud.dal.mysql.model;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import lombok.Data;

/**
 * 用户账号
 *
 * @author Levi
 * @since 0.0.1
 **/
@Data
@Entity
@Table(name = "user_account", indexes = {@Index(name = "email_index", columnList = "email")})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @NotNull(message = "用户邮箱不能为空")
    private String email;

    @NotNull(message = "用户密码不能为空")
    private String password;

    private String nickname;

    private String address;

    @NotNull(message = "账户创建时间不为空")
    private Date createTime;

    @Generated(GenerationTime.INSERT)
    private Date updateTime;
}
