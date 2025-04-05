package com.huangzizhu.exception;

public class AddMusicToLIstFailException extends RuntimeException {
    public AddMusicToLIstFailException(String message) {
        super(message);
    }
    public AddMusicToLIstFailException(String message, Throwable cause) {super(message, cause);}
}
