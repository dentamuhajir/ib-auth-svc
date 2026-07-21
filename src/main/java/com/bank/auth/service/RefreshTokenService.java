package com.bank.auth.service;

import com.bank.auth.repository.RefreshTokenRepository;
import com.bank.auth.security.jwt.TokenHashService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository repository;

    private final TokenHashService hashService;
}
