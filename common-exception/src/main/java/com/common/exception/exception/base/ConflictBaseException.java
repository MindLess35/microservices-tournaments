package com.common.exception.exception.base;

public class ConflictBaseException extends BaseException {
    public ConflictBaseException(String message) {
        super(409, message);
    }

    public ConflictBaseException(String message, Throwable cause) {
        super(message, cause, 409);
    }
}
