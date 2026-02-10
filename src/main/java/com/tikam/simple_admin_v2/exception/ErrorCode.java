package com.tikam.simple_admin_v2.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not found"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Authorization failed"),
    TOKEN_EXPIRED(HttpStatus.NOT_FOUND, "JWT Token Expired");



    private final HttpStatus status;
    private final String message;
}
