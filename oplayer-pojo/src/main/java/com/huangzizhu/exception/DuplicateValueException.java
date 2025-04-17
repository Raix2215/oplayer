package com.huangzizhu.exception;

/**
 * 自定义数据库插入时重复异常
 * @Author huangzizhu
 * @Version 1.0
 */
public class DuplicateValueException extends RuntimeException {
    public DuplicateValueException(String message) {
        super(message);
    }

    public DuplicateValueException(String message, Throwable cause) {
        super(message, cause);
    }
}