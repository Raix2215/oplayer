package com.huangzizhu.pojo;

import com.huangzizhu.exception.DuplicateValueException;
import com.huangzizhu.exception.PasswordErrorException;
import com.huangzizhu.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
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

    private String extractValue(String message,String regex,int index) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(index); // 返回重复值
        }
        return null;
    }
}
