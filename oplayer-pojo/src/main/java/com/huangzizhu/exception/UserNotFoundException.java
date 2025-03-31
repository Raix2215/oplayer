package com.huangzizhu.exception;

/**
 * 用户不存在
 * @Author huangzizhu
 * @Version 1.0
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(String message, Throwable cause) {
        super(message,cause);
    }
}
