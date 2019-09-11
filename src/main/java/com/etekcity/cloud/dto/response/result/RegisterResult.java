package com.etekcity.cloud.dto.response.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * 用户注册返回的结果
 *
 * @author Levi
 * @date 2019/7/29/029
 * @since 0.0.1
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RegisterResult {
    private Long userId;
    private Date creatAt;

}
