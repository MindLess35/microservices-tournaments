package com.common.exception.exception.base;

public class UnauthorizedBaseException extends BaseException {
    public UnauthorizedBaseException(String message) {
        super(401, message);
    }

    public UnauthorizedBaseException(String message, Throwable cause) {
        super(message, cause, 401);
    }
}
