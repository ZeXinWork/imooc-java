package com.imooc.first.domain.exception;

import com.imooc.first.domain.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述： 处理统一异常的handler
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        log.error("系统异常: ", e);
        return ApiRestResponse.error(500, "系统异常，请稍后重试");
    }

    @ExceptionHandler(ImoocMallException.class)
    @ResponseBody
    public Object handleImoocMallException(ImoocMallException e) {
        log.error("业务异常: ", e);
        // 捕获 ImoocMallException 并返回具体的错误信息和状态码
        return ApiRestResponse.error(e.getCode(), e.getMessage());
    }
}
