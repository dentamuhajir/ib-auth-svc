package com.bank.auth.controller;


import com.bank.auth.common.ApiResponse;
import com.bank.auth.common.ResponseCode;
import com.bank.auth.dto.request.LoginRequest;
import com.bank.auth.dto.request.RegisterRequest;
import com.bank.auth.dto.response.LoginResponse;
import com.bank.auth.dto.response.RegisterResponse;
import com.bank.auth.security.userdetails.AppUserDetails;
import com.bank.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

        LoginResponse loginResponse = authService.login(
                req.getUsername(),
                req.getPassword()
        );

           return ApiResponse.<LoginResponse>builder()
                   .success(true)
                   .code(ResponseCode.SUCCESS)
                   .message("Login successful")
                   .data(loginResponse)
                   .build();
    }

    @PostMapping("/register")
    public ApiResponse<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {

        RegisterResponse registerResponse = authService.register(request);

        return ApiResponse.<RegisterResponse>builder()
                .success(true)
                .code(ResponseCode.SUCCESS)
                .message("User registered successfully")
                .data(registerResponse)
                .build();
    }

    @GetMapping("/me")
    public String me(@AuthenticationPrincipal AppUserDetails user) {
        return "Hello, " + user.getUsername();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String Admin(@AuthenticationPrincipal AppUserDetails user) {
        return "Hello, Admin " + user.getUsername();
    }

    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @GetMapping("/customer")
    public String Customer(@AuthenticationPrincipal AppUserDetails user) {
        return "Hello, Customer " + user.getUsername();
    }
}