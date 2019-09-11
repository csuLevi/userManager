package com.etekcity.cloud.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etekcity.cloud.constant.ResponseConstant;
import com.etekcity.cloud.dto.response.Response;
import com.etekcity.cloud.exception.ServerException;

/**
 * 全局异常处理器
 *
 * @author Levi
 * @date 2019/8/3/003
 * @since 1.1.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理不可知异常
     *
     * @return Response
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response handleException(Exception e) {
        log.error("internal error", e);
        return ResponseConstant.INTERNAL_ERROR_RESPONSE;
    }

    /**
     * 处理ServerException
     *
     * @param e ServerException
     * @return Response
     */
    @ExceptionHandler(ServerException.class)
    @ResponseBody
    public Response handleServerException(ServerException e) {
        log.error("error info: {}", e.getMsg(), e);
        return ResponseConstant.INTERNAL_ERROR_RESPONSE;
    }
}
