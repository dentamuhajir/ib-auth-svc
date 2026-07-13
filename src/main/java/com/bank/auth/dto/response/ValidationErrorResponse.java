package com.bank.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Map;

@Getter
@Builder
public class ValidationErrorResponse {

    private Instant timestamp;

    private int status;

    private String error;

    private Map<String, String> errors;

    private String path;
}