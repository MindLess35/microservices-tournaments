package com.microservices.user.exceptionhandler;

import com.common.exception.exception.response.ResponseViolationErrorBody;
import com.microservices.user.exception.InvalidPasswordException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ResponseViolationErrorBody> handleInvalidPasswordException(InvalidPasswordException e, HttpServletRequest request) {
        ResponseEntity<ResponseViolationErrorBody> result = buildViolationErrorBody(HttpStatus.BAD_REQUEST, e, request);
        result.getBody().setFieldErrors(Map.of(e.getInvalidFieldName(), e.getMessage()));
        return result;
    }

    private static ResponseEntity<ResponseViolationErrorBody> buildViolationErrorBody(HttpStatus httpStatus, Exception e, HttpServletRequest request) {
        log.debug(e.getMessage(), e);
        return ResponseEntity.status(httpStatus)
                .body(ResponseViolationErrorBody.builder()
                        .status(httpStatus.value())
                        .cause(httpStatus.getReasonPhrase())
                        .exception(e.getClass().getName())
                        .path(request.getRequestURI())
                        .build());
    }
}
