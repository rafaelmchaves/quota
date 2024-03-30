package com.vicarius.quota.exceptions;

import org.springframework.http.HttpStatus;

@ExceptionMetadata(httpStatus = HttpStatus.SERVICE_UNAVAILABLE)
public class UnavailableErrorException extends CustomException {

    public UnavailableErrorException(String message, Throwable throwable) {
        super(message, "3", throwable);
    }
}
