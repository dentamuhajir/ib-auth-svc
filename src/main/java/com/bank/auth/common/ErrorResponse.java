package com.bank.auth.common;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Map;

@Getter
@Builder
public class ErrorResponse {

    private boolean success;

    private String code;

    private String message;

    private Map<String, String> errors;

    @Builder.Default
    private Instant timestamp = Instant.now();

}