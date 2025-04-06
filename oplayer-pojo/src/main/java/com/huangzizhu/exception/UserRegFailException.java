package com.huangzizhu.exception;

public class UserRegFailException extends RuntimeException {
    public UserRegFailException(String message) {
        super(message);
    }
    public UserRegFailException(String message, Throwable cause) {super(message, cause);}
}
