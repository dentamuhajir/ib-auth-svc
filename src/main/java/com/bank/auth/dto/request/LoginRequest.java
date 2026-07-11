package com.bank.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank
    @Size(min = 3)
    private String username;
    @NotBlank
    private String password;
}
