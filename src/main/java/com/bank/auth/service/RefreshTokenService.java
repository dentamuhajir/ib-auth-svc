package com.bank.auth.service;

import com.bank.auth.entity.RefreshToken;
import com.bank.auth.entity.User;
import com.bank.auth.repository.RefreshTokenRepository;
import com.bank.auth.security.jwt.RefreshTokenGenerator;
import com.bank.auth.security.jwt.TokenHashService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository repository;

    private final RefreshTokenGenerator generator;
    private final TokenHashService hashService;

    @Value("${spring.jwt.refresh-ttl-ms}")
    private long refreshTtl;

    @Transactional
    public String create(User user) {

        String rawToken = generator.generate();

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .tokenHash(hashService.hash(rawToken))
                .expiresAt(
                        LocalDateTime.now()
                                .plus(refreshTtl, ChronoUnit.MILLIS)
                )
                .revoked(false)
                .build();

        repository.save(refreshToken);

        return rawToken;
    }

}
