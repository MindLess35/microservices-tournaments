package com.common.exception.exception.base;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    protected final int status;

    public BaseException(int status, String message) {
        super(message);
        this.status = status;
    }

    public BaseException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }
}
