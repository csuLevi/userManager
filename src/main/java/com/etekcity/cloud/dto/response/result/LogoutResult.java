package com.etekcity.cloud.dto.response.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 用户登出响应体
 *
 * @author Levi
 * @date 2019/7/30/030
 * @since 0.0.1
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class LogoutResult {
}
