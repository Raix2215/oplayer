package com.huangzizhu.exception;

public class PlayListException extends RuntimeException {
    public PlayListException(String message) {
        super(message);
    }
    public PlayListException(String message, Throwable cause) {
        super(message, cause);
    }
}
