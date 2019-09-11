package com.etekcity.cloud.service.impl.handler.enhance;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * AOP日志切面
 *
 * @author Levi
 * @date 2019/8/2/002
 * @since 0.0.1
 */
@Slf4j
@Order(1)
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.etekcity.cloud.service.impl.handler.*.*(..)) && args(requestHeader, requestBody)")
    public void handlerTwoArgsRequestPointCut(String requestHeader, Object requestBody) {
    }

    @Around(value = "handlerTwoArgsRequestPointCut(requestHeader, requestBody)")
    public Object doLoggingTwoArgs(ProceedingJoinPoint pjp, String requestHeader, Object requestBody) throws Throwable {
        Logger log = LoggerFactory.getLogger(getClass());
        //log request
        log.info("{} start, request-header is:{}, request-body is:{}",
                pjp.getTarget().getClass().getName(), requestHeader, requestBody);

        Object response = pjp.proceed();
        //log request and response
        log.info("{} finish, the request-header is: {}, request-body is: {}, response-result is: {}",
                pjp.getTarget().getClass().getName(), requestHeader, requestBody, response);
        return response;
    }

    @Pointcut("execution(* com.etekcity.cloud.service.impl.handler.*.*(..)) && args(request)")
    public void handlerOneArgRequestPointCut(Object request) {
    }

    @Around(value = "handlerOneArgRequestPointCut(request)")
    public Object doLoggingBodyArg(ProceedingJoinPoint pjp, Object request) throws Throwable {
        Logger log = LoggerFactory.getLogger(getClass());
        //log request
        log.info("{} start, request-body is:{}", pjp.getTarget().getClass().getName(), request);

        Object response = pjp.proceed();
        //log request and response
        log.info("{} finish, the request-body is: {}, response-result is: {}",
                pjp.getTarget().getClass().getName(), request, response);
        return response;
    }
}
