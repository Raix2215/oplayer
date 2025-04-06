package com.huangzizhu.exception;

import lombok.Data;

import java.io.File;

@Data
public class MusicParseException extends RuntimeException {
    private File file;
    public MusicParseException(String message) {
        super(message);
    }
    public MusicParseException(String message, File file) {super(message);}
    public MusicParseException(String message, File file,Throwable cause) {super(message,cause);}
    public MusicParseException(String message, Throwable cause) {super(message,cause);}
}
