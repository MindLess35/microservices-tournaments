package com.microservices.user.exceptionhandler;

import com.common.exception.exception.response.ResponseViolationErrorBody;
import com.microservices.user.exception.InvalidPasswordException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseViolationErrorBody handleInvalidPasswordException(InvalidPasswordException e, HttpServletRequest request) {
        ResponseViolationErrorBody result = buildViolationErrorBody(HttpStatus.BAD_REQUEST, e, request);
        result.setFieldErrors(Map.of(e.getInvalidFieldName(), e.getMessage()));
        return result;
    }

    private static ResponseViolationErrorBody buildViolationErrorBody(HttpStatus httpStatus, Exception e, HttpServletRequest request) {
        log.debug(e.getMessage(), e);
        return ResponseViolationErrorBody.builder()
                .status(httpStatus.value())
                .cause(httpStatus.getReasonPhrase())
                .exception(e.getClass().getName())
                .path(request.getRequestURI())
                .build();
    }
}
