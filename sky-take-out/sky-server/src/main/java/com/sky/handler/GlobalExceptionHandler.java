package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler
    public Result SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException sqlexception) {
//        Duplicate entry 'admin' for key 'idx_username'
//        Duplicate entry 'admin' for key 'idx_username'
        String message = sqlexception.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] messageArray = message.split(" ");
            String error = messageArray[2] + MessageConstant.ALREADY_EXIST;
            return Result.error(error);
        }
        else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }
}
