package com.bank.auth.controller;


import com.bank.auth.dto.request.LoginRequest;
import com.bank.auth.dto.response.LoginResponse;
import com.bank.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest req) {

        String token = authService.login(
                req.getUsername(),
                req.getPassword()
        );

           return LoginResponse.builder()
                   .accessToken(token)
                   .tokenType("Bearer")
                   .build();
    }

    @GetMapping("/me")
    public String me() {
        return "Hello, User!";
    }
}