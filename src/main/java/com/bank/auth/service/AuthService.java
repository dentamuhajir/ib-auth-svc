package com.bank.auth.service;


import com.bank.auth.dto.response.LoginResponse;
import com.bank.auth.entity.User;
import com.bank.auth.exception.InvalidCredentialException;
import com.bank.auth.exception.UserNotFoundException;
import com.bank.auth.repository.UserRepository;
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
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public LoginResponse login(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );

           AppUserDetails principal =
                   (AppUserDetails)
                           authentication.getPrincipal();

           User user =
                    userRepository.findByUsername(
                            principal.getUsername()
                    ).orElseThrow(UserNotFoundException::new);

           LoginResponse loginResponse = LoginResponse.builder()
                   .accessToken(jwtService.generateToken(principal))
                   .tokenType("Bearer")
                   .expiresIn(jwtService.getExpiration())
                   .refreshToken(refreshTokenService.create(user))
                   .build();

           return loginResponse;

        }  catch (BadCredentialsException e) {
            throw new InvalidCredentialException();
        }
    }
}