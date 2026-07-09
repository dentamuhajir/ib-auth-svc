package com.bank.auth.service;


import com.bank.auth.security.jwt.JwtService;
import com.bank.auth.security.userdetails.AppUserDetails;
import com.bank.auth.security.userdetails.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    public String login(String username, String password) {

        var userDetails = (AppUserDetails)
                userDetailsService.loadUserByUsername(username);

        if (!encoder.matches(password, userDetails.getPassword())) {
            throw new RuntimeException("INVALID_CREDENTIALS");
        }

        return jwtService.generateToken(userDetails);
    }
}