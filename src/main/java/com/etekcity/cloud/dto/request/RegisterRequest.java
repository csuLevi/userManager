package com.etekcity.cloud.dto.request;

import lombok.Data;

/**
 * 注册请求体
 *
 * @author Levi
 * @date 2019/8/1/001
 * @since 0.0.1
 */
@Data
public class RegisterRequest {
    private String email;
    private String password;
}
