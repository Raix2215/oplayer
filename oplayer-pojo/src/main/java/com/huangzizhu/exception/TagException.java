package com.huangzizhu.exception;

public class TagException extends RuntimeException {
    public TagException(String message) {
        super(message);
    }
    public TagException(String message, Throwable cause) {
        super(message, cause);
    }
}
