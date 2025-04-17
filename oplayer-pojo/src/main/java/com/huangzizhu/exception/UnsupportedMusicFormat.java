package com.huangzizhu.exception;

public class UnsupportedMusicFormat extends RuntimeException{
    public UnsupportedMusicFormat(String message) {super(message);}
    public UnsupportedMusicFormat(String message, Throwable cause) {super(message, cause);}
    public UnsupportedMusicFormat(Throwable cause) {super(cause);}

}
