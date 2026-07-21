package com.bank.auth.security.jwt;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class RefreshTokenGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generate() {

        byte[] bytes = new byte[32]; // 256 bit

        RANDOM.nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }
}
