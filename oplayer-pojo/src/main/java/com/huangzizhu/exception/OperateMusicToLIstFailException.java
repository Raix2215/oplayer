package com.huangzizhu.exception;

public class OperateMusicToLIstFailException extends RuntimeException {
    public OperateMusicToLIstFailException(String message) {
        super(message);
    }
    public OperateMusicToLIstFailException(String message, Throwable cause) {super(message, cause);}
}
