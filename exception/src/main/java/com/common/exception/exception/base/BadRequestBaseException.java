package com.common.exception.exception.base;

public class BadRequestBaseException extends BaseException {

    public BadRequestBaseException(String message) {
        super(400, message);
    }

}
