package com.etekcity.cloud.dto.request;

import lombok.Data;

/**
 * 更新用户信息请求
 *
 * @author Levi
 * @date 2019/7/30/030
 * @since 0.0.1
 */
@Data
public class UpdateInfoRequest {
    private String nickname;
    private String address;
}
