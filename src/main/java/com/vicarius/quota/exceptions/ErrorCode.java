package com.vicarius.quota.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    MAXIMUM_QUOTA("QT01"),
    USER_NOT_FOUND("QT02"),
    UNAVAILABLE("QT03");

    private final String code;


}
