package com.bank.auth.controller;


import com.bank.auth.common.ApiResponse;
import com.bank.auth.common.ResponseCode;
import com.bank.auth.dto.request.LoginRequest;
import com.bank.auth.dto.response.LoginResponse;
import com.bank.auth.security.userdetails.AppUserDetails;
import com.bank.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest req) {

        String token = authService.login(
                req.getUsername(),
                req.getPassword()
        );

           LoginResponse loginResponse = LoginResponse.builder()
                   .accessToken(token)
                   .tokenType("Bearer")
                   .build();

           return ApiResponse.<LoginResponse>builder()
                   .success(true)
                   .code(ResponseCode.SUCCESS)
                   .message("Login successful")
                   .data(loginResponse)
                   .build();
    }

    @GetMapping("/me")
    public String me(@AuthenticationPrincipal AppUserDetails user) {
        return "Hello, " + user.getUsername();
    }
}