package com.common.exception.exception.base;

public class NotFoundBaseException extends BaseException {

    public NotFoundBaseException(String message) {
        super(404, message);
    }

}
