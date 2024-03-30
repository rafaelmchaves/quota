package com.vicarius.quota.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@ExceptionMetadata(httpStatus = HttpStatus.TOO_MANY_REQUESTS)
public class MaximumQuotaException extends CustomException {

    public MaximumQuotaException(String message) {
        super(message, "1");
    }

}
