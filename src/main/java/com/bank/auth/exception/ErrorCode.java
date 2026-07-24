package com.bank.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    SUCCESS("IB-200-00", "Success", HttpStatus.OK),
    VALIDATION_ERROR("IB-400-01", "Validation failed", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIAL("IB-401-01", "Invalid username or password", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED("IB-401-02", "Token has expired", HttpStatus.UNAUTHORIZED),
    ACCOUNT_LOCKED("IB-403-01", "Account is locked due to multiple failed attempts", HttpStatus.FORBIDDEN),
    USER_NOT_FOUND("IB-404-01", "User not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS("IB-409-01", "User already exists", HttpStatus.CONFLICT),
    EMAIL_ALREADY_EXISTS("IB-409-02", "Email already exists", HttpStatus.CONFLICT),
    INTERNAL_SERVER_ERROR("IB-500-00", "An internal system error occurred", HttpStatus.INTERNAL_SERVER_ERROR);


    private final String code;
    private final String defaultMessage;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String defaultMessage, HttpStatus httpStatus) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.httpStatus = httpStatus;
    }
}