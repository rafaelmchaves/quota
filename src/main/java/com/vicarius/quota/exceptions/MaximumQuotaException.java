package com.vicarius.quota.exceptions;

import org.springframework.http.HttpStatus;

@ExceptionMetadata(httpStatus = HttpStatus.TOO_MANY_REQUESTS)
public class MaximumQuotaException extends CustomException {

    public MaximumQuotaException(String message) {
        super(message, ErrorCode.MAXIMUM_QUOTA.getCode());
    }

}
