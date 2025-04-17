package com.huangzizhu.exception;

public class FIleException extends RuntimeException {
    public FIleException(String message) {
        super(message);
    }
    public FIleException(String message, Throwable cause) {
        super(message, cause);
    }
}
