package com.etekcity.cloud.constant;

import com.etekcity.cloud.constant.errorCode.ErrorCode;
import com.etekcity.cloud.dto.response.result.VoidResult;
import com.etekcity.cloud.dto.response.Response;

/**
 * 常用到的响应体常量
 *
 * @author Levi
 * @date 2019/7/29/029
 * @since 0.0.1
 */
public class ResponseConstant {
    private static final VoidResult VOID_RESULT = new VoidResult();
    public static final Response SUCCESS_RESPONSE = new Response<>(ErrorCode.SUCCESS, VOID_RESULT);
    public static final Response INTERNAL_ERROR_RESPONSE = new Response<>(ErrorCode.INTERNAL_SERVER_ERROR, VOID_RESULT);

}
