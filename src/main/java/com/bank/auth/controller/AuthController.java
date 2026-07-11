package com.bank.auth.controller;


import com.bank.auth.dto.request.LoginRequest;
import com.bank.auth.dto.response.LoginResponse;
import com.bank.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("Password123@"));

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