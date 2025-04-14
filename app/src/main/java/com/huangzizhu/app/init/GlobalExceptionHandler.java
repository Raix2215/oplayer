package com.huangzizhu.app.init;

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
    //处理重复值异常
    @ExceptionHandler
    public Result handleException(DuplicateValueException e) {
        log.error("重复值异常", e);
        String value = extractValue(e.getMessage(),"Duplicate entry '([^']*)' for key",1);
        return Result.error("重复的值:\' "+value+" \'");
    }
    //处理用户不存在异常
    @ExceptionHandler
    public Result handleException(UserNotFoundException e) {
        log.error("用户信息错误异常", e);
        return Result.error("用户信息错误\'"+e.getMessage()+"\'");
    }
    //处理密码错误异常
    @ExceptionHandler
    public Result handleException(PasswordErrorException e) {
        log.error("登陆信息错误异常", e);
        return Result.error(e.getMessage());
    }
    //处理权限不足异常
    @ExceptionHandler
    public Result handleException(PermissionDenyException e) {
        log.error("权限错误异常", e);
        return Result.error(e.getMessage());
    }
    //处理音乐资源获取异常（可能是音乐不存在）
    @ExceptionHandler
    public Result handleException(GetMusicResourceException e) {
        log.error("获取音乐资源异常", e);
        return Result.error(e.getMessage());
    }
    //处理用户注册异常
    @ExceptionHandler
    public Result handleException(UserRegFailException e) {
        log.error("用户注册发生异常", e);
        return Result.error(e.getMessage());
    }
    //操作音乐至歌单异常
    @ExceptionHandler
    public Result handleException(OperateMusicToLIstFailException e) {
        log.error("操作音乐至收藏错误", e);
        return Result.error(e.getMessage());
    }
    //处理参数异常
    @ExceptionHandler
    public Result handleException(ParamInvalidException e) {
        log.error("入参异常", e);
        return Result.error(e.getMessage());
    }
    //处理歌单操作异常
    @ExceptionHandler
    public Result handleException(PlayListException e) {
        log.error("歌单操作异常", e);
        return Result.error(e.getMessage());
    }
    // 处理评论异常
    @ExceptionHandler
    public Result handleException(CommentException e) {
        log.error("评论操作异常", e);
        return Result.error(e.getMessage());
    }
    // 处理标签异常
    @ExceptionHandler
    public Result handleException(TagException e) {
        log.error("标签操作异常", e);
        return Result.error(e.getMessage());
    }

    //处理文件异常
    @ExceptionHandler
    public Result handleException(FIleException e) {
        log.error("文件异常", e);
        return Result.error(e.getMessage());
    }

    // 处理邮件异常
    @ExceptionHandler
    public Result handleException(EmailException e) {
        log.error("邮件异常", e);
        return Result.error(e.getMessage());
    }





    //处理音乐解析异常
    @ExceptionHandler
    public void handleException(MusicParseException e) {
        log.error("音乐解析异常", e);
        log.error("出错音乐文件{} " , e.getFile().getAbsoluteFile());
    }

    //处理服务器启动过程中初始化异常
    @ExceptionHandler
    public void handleException(InitException e) {
        log.error("初始化异常", e);
        System.exit(-1);
    }


    //用于提取重复异常的重复值
    private String extractValue(String message,String regex,int index) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(index); // 返回重复值
        }
        return null;
    }
}
