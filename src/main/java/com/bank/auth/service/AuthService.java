package com.bank.auth.service;


import com.bank.auth.exception.InvalidCredentialException;
import com.bank.auth.exception.UserNotFoundException;
import com.bank.auth.security.jwt.JwtService;
import com.bank.auth.security.userdetails.AppUserDetails;
import com.bank.auth.security.userdetails.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    public String login(String username, String password) {

        AppUserDetails appUserDetails;

        try {
            appUserDetails = (AppUserDetails) userDetailsService.loadUserByUsername(username);
        }  catch (UserNotFoundException e) {
            throw new InvalidCredentialException();
        }

        if (!encoder.matches(password, appUserDetails.getPassword())) {
            throw new InvalidCredentialException();
        }

        return jwtService.generateToken(appUserDetails);
    }
}