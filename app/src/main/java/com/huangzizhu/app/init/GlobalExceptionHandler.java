package com.huangzizhu.app;

import com.huangzizhu.exception.*;
import com.huangzizhu.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 全局异常处理
 * @Author huangzizhu
 * @Version 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private ApplicationContext applicationContext;

    @ExceptionHandler
    public Result handleException(Exception e) {
        log.error("发生异常", e);
        return Result.error("服务器异常");
    }
    @ExceptionHandler
    public Result handleException(DuplicateValueException e) {
        log.error("重复值异常", e);
        String value = extractValue(e.getMessage(),"Duplicate entry '([^']*)' for key",1);
        return Result.error("重复的值:\' "+value+" \'");
    }
    @ExceptionHandler
    public Result handleException(UserNotFoundException e) {
        log.error("用户信息错误异常", e);
        return Result.error("用户信息错误\'"+e.getMessage()+"\'");
    }

    @ExceptionHandler
    public Result handleException(PasswordErrorException e) {
        log.error("登陆信息错误异常", e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler
    public Result handleException(PermissionDenyException e) {
        log.error("权限错误异常", e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler
    public void handleException(InitException e) {
        log.error("初始化异常", e);
        System.exit(-1);
    }


    private String extractValue(String message,String regex,int index) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(index); // 返回重复值
        }
        return null;
    }
}
