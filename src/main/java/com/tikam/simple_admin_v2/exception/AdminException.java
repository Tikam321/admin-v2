package com.tikam.simple_admin_v2.exception;

import lombok.Getter;

@Getter
public class AdminException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public AdminException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public AdminException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
