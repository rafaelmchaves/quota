package com.vicarius.quota.exceptions;


import org.springframework.http.HttpStatus;

@ExceptionMetadata(httpStatus = HttpStatus.BAD_REQUEST)
public class UserNotFoundException extends CustomException {

    public UserNotFoundException(String message) {
        super(message, ErrorCode.USER_NOT_FOUND.getCode());
    }
}
