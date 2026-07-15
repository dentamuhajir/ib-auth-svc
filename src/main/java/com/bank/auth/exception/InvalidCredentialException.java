package com.bank.auth.exception;

public class InvalidCredentialException extends BusinessException {
    public InvalidCredentialException() {
        super(
                ErrorCode.INVALID_CREDENTIAL,
                "invalid username or password"
        );
    }
}
