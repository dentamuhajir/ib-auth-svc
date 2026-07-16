package com.bank.auth.service;


import com.bank.auth.exception.InvalidCredentialException;
import com.bank.auth.exception.UserNotFoundException;
import com.bank.auth.security.jwt.JwtService;
import com.bank.auth.security.userdetails.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String login(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );

           AppUserDetails user =
                   (AppUserDetails)
                           authentication.getPrincipal();

           return jwtService.generateToken(user);

        }  catch (BadCredentialsException e) {
            throw new InvalidCredentialException();
        }
    }
}