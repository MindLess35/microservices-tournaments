package com.common.exception.exceptionhandler;

import com.common.exception.exception.base.BadRequestBaseException;
import com.common.exception.exception.base.ResourceNotFoundException;
import com.common.exception.exception.response.ResponseErrorBody;
import com.common.exception.exception.response.ResponseViolationErrorBody;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.PAYLOAD_TOO_LARGE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class,
            HttpServerErrorException.class})
    public ResponseEntity<ResponseErrorBody> handleUnexpectedException(Exception e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        return buildErrorBody(INTERNAL_SERVER_ERROR, e, request);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseErrorBody> handleMaxUploadSizeException(MaxUploadSizeExceededException e,
                                                                          HttpServletRequest request) {
        return buildErrorBody(PAYLOAD_TOO_LARGE, e, request);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            BadRequestBaseException.class,
            HttpMediaTypeNotSupportedException.class,
            TypeMismatchException.class,
//            PropertyReferenceException.class,
            MissingRequestValueException.class,
            HttpMediaTypeNotAcceptableException.class,
            NoResourceFoundException.class,
            HandlerMethodValidationException.class,})
    public ResponseEntity<ResponseErrorBody> handleBadRequestException(Exception e, HttpServletRequest request) {
        return buildErrorBody(BAD_REQUEST, e, request);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseErrorBody> handleNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        return buildErrorBody(NOT_FOUND, e, request);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseErrorBody> handleHttpClientErrorException(HttpClientErrorException e, HttpServletRequest request) {
        return switch (e.getStatusCode().value()) {
            case 401 -> buildErrorBody(UNAUTHORIZED, e, request);
            case 403 -> buildErrorBody(FORBIDDEN, e, request);
            case 404 -> buildErrorBody(NOT_FOUND, e, request);
            case 413 -> buildErrorBody(PAYLOAD_TOO_LARGE, e, request);
            default -> buildErrorBody(BAD_REQUEST, e, request);
        };
    }
//    @ExceptionHandler({AuthenticationException.class})
//    public ResponseEntity<ResponseErrorBody> handleUnauthorizedException(Exception e, HttpServletRequest request) {
//        return buildErrorBody(UNAUTHORIZED, e, request);
//    }
//
//    @ExceptionHandler({AccessDeniedException.class})
//    public ResponseEntity<ResponseErrorBody> handleAccessDeniedException(Exception e, HttpServletRequest request) {
//        return buildErrorBody(FORBIDDEN, e, request);
//    }

    @ExceptionHandler
    public ResponseEntity<ResponseViolationErrorBody> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        ResponseEntity<ResponseViolationErrorBody> result = buildViolationErrorBody(BAD_REQUEST, e, request);

        result.getBody().setFieldErrors(e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, ex -> ex.getDefaultMessage() == null
                                ? "Invalid"
                                : ex.getDefaultMessage(),
                        (message, anotherMessage) -> message + " and " + anotherMessage)));
        return result;
    }

    private static ResponseEntity<ResponseErrorBody> buildErrorBody(HttpStatus httpStatus, Exception e, HttpServletRequest request) {
        if (!httpStatus.is5xxServerError())
            log.debug(e.getMessage(), e);
        return ResponseEntity.status(httpStatus)
                .body(ResponseErrorBody.builder()
                        .status(httpStatus.value())
                        .cause(httpStatus.getReasonPhrase())
                        .exception(e.getClass().getName())
                        .message(e.getMessage())
                        .path(request.getRequestURI())
                        .build());
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

