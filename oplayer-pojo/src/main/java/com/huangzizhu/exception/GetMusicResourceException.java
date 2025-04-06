package com.huangzizhu.exception;

public class GetMusicResourceException extends RuntimeException {
    public GetMusicResourceException(String message) {
        super(message);
    }
    public GetMusicResourceException(String message,Throwable cause) {super(message,cause);}
}
