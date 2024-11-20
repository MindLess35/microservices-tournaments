package com.common.exception.exception.base;

public class BadRequestBaseException extends RuntimeException {
    public BadRequestBaseException(String message) {
        super(message);
    }

    public BadRequestBaseException(Throwable cause) {
        super(cause);
    }

    public BadRequestBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
