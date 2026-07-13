package com.bank.auth.common;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class ApiResponse<T> {

    private boolean success;

    private String code;

    private String message;

    private T data;

    @Builder.Default
    private Instant timestamp = Instant.now();

}