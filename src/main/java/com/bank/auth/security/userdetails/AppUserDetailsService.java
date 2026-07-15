package com.bank.auth.security.userdetails;


import com.bank.auth.entity.User;
import com.bank.auth.exception.UserNotFoundException;
import com.bank.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User users = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());

        return new AppUserDetails(
                users.getId().toString(),
                users.getUsername(),
                users.getPasswordHash()
        );
    }
}