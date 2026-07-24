package com.bank.auth.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Builder
public class RegisterResponse {
    private UUID id;
    private String username;
    private String email;
    private String fullName;
}
