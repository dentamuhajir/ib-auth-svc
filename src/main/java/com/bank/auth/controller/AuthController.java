package com.bank.auth.controller;


import com.bank.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> req) {

        String token = authService.login(
                req.get("username"),
                req.get("password")
        );

           return Map.of(
                "accessToken", token,
                "tokenType", "Bearer"
        );
    }

    @GetMapping("/me")
    public String me() {
        return "Hello, User!";
    }
}