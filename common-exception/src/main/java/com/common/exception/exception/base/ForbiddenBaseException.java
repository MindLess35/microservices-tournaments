package com.common.exception.exception.base;

public class ForbiddenBaseException extends BaseException {
    public ForbiddenBaseException(String message) {
        super(403, message);
    }

    public ForbiddenBaseException(String message, Throwable cause) {
        super(message, cause, 403);
    }
}
