package com.vicarius.quota.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { CustomException.class })
    public ResponseEntity<Object> handle(CustomException exception, WebRequest request) {
        ExceptionMetadata exceptionMetadata = exception.getClass().getAnnotation(ExceptionMetadata.class);

        if (Objects.isNull(exceptionMetadata)) {
            throw exception;
        }

        final var errorMessage = new ErrorMessage(exception.getMessage(), exception.getCode());
        HttpStatus httpStatus = exceptionMetadata.httpStatus();
        return handleExceptionInternal(exception, errorMessage, new HttpHeaders(), httpStatus, request);
    }

}

record ErrorMessage (String message, String errorCode) {
}