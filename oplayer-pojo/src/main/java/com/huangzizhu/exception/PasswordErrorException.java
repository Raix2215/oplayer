package com.huangzizhu.exception;

/**
 * 密码错误异常
 * @Author huangzizhu
 * @Version 1.0
 */
public class PasswordErrorException extends RuntimeException {
    public PasswordErrorException(String message) {
        super(message);
    }
    public PasswordErrorException(String message, Throwable cause) {
        super(message,cause);
    }
}
