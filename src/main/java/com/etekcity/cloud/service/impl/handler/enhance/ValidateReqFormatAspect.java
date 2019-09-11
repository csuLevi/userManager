package com.etekcity.cloud.service.impl.handler.enhance;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.etekcity.cloud.constant.MethodConstant;
import com.etekcity.cloud.constant.errorCode.ErrorCode;
import com.etekcity.cloud.dto.response.Response;
import com.etekcity.cloud.dto.response.result.VoidResult;
import com.etekcity.cloud.util.StrUtil;

/**
 * 判断请求头是否正确
 * 正确格式为： {userId} {token}
 * 判断依据为： 切分请求头得到一个长度为2的字符串数组
 *
 * @author Levi
 * @date 2019/8/2/002
 * @since 0.0.1
 */
@Aspect
@Order(2)
@Component
public class ValidateReqFormatAspect {
    @Pointcut("execution(* com.etekcity.cloud.service.impl.handler.*.*(..)) && args(requestHeader)")
    public void validateRequestPointCut(String requestHeader) {
    }

    @Around(value = "validateRequestPointCut(requestHeader)")
    public Object validateRequestHeader(ProceedingJoinPoint pjp, String requestHeader) throws Throwable {
        String[] requestList = StrUtil.split(requestHeader, StrUtil.SPLIT_CHAR_SPACE);
        if (MethodConstant.REQUEST_HEADER_LIST_LENGTH != requestList.length) {
            return new Response<>(ErrorCode.INTERNAL_SERVER_TIMEOUT_ERROR, new VoidResult());
        }
        return pjp.proceed();
    }

    @Pointcut("execution(* com.etekcity.cloud.service.impl.handler.*.*(..)) && args(requestHeader, requestBody)")
    public void validateRequestPointCut0(String requestHeader, Object requestBody) {
    }

    @Around(value = "validateRequestPointCut0(requestHeader, requestBody)")
    public Object validateRequestHeader0(ProceedingJoinPoint pjp, String requestHeader, Object requestBody) throws Throwable {
        String[] requestList = StrUtil.split(requestHeader, StrUtil.SPLIT_CHAR_SPACE);
        if (MethodConstant.REQUEST_HEADER_LIST_LENGTH != requestList.length) {
            return new Response<>(ErrorCode.INTERNAL_SERVER_TIMEOUT_ERROR, new VoidResult());
        }
        return pjp.proceed();
    }
}