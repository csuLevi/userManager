package com.etekcity.cloud.dto.request;

import lombok.Data;

/**
 * 更新密码请求
 *
 * @author Levi
 * @date 2019/7/30/030
 * @since 0.0.1
 */
@Data
public class UpdatePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
