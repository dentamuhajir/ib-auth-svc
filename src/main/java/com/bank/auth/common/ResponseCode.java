package com.bank.auth.common;

public final class ResponseCode {

    private ResponseCode() {
    }

    public static final String SUCCESS = "SUCCESS";

    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";

    public static final String INVALID_CREDENTIAL = "INVALID_CREDENTIAL";

    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";

}