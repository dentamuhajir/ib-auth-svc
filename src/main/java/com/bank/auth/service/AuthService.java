package com.bank.auth.service;


import com.bank.auth.dto.request.RegisterRequest;
import com.bank.auth.dto.response.LoginResponse;
import com.bank.auth.dto.response.RegisterResponse;
import com.bank.auth.entity.*;
import com.bank.auth.exception.BusinessException;
import com.bank.auth.exception.ErrorCode;
import com.bank.auth.exception.InvalidCredentialException;
import com.bank.auth.exception.UserNotFoundException;
import com.bank.auth.repository.RoleRepository;
import com.bank.auth.repository.UserRepository;
import com.bank.auth.repository.UserRoleRepository;
import com.bank.auth.security.jwt.JwtService;
import com.bank.auth.security.userdetails.AppUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

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

        } catch (BadCredentialsException e) {
            throw new InvalidCredentialException();
        }

    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException(ErrorCode.USER_ALREADY_EXISTS ,"Username already exists");
        }

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS ,"Email already exists");
        }

        Role customerRole = roleRepository.findByName("ROLE_CUSTOMER")
                .orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "Customer role not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());

        user.setStatus(UserStatus.ACTIVE);
        user.setFailedLoginAttempt(0);

        User savedUser = userRepository.save(user);

        UserRole userRole = UserRole.builder()
                .id(new UserRoleId(savedUser.getId(), customerRole.getId()))
                .user(savedUser)
                .role(customerRole)
                .build();

        userRoleRepository.save(userRole);

        return RegisterResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .fullName(savedUser.getFullName())
                .build();
    }


}