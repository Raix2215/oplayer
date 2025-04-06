package com.huangzizhu.exception;

public class MusicDeleteException extends RuntimeException {
    public MusicDeleteException(String message) {
        super(message);
    }
    public MusicDeleteException(String message,Throwable cause) {super(message,cause);}
}
