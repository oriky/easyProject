package com.zhousj.demo.exception;

import java.io.Serializable;

/**
 *校验异常，校验失败时抛出
 * @author zhousj
 */
public class ValidateException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -43664323427710L;

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }
}
