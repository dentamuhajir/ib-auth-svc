package com.bank.auth.exception;

public enum ErrorCode {
    SUCCESS,

    VALIDATION_ERROR,

    INVALID_CREDENTIAL,

    USER_NOT_FOUND,

    ACCOUNT_LOCKED,

    TOKEN_EXPIRED,

    INTERNAL_SERVER_ERROR
}
