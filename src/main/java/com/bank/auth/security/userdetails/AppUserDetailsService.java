package com.bank.auth.security.userdetails;


import com.bank.auth.entity.Role;
import com.bank.auth.entity.User;
import com.bank.auth.entity.UserRole;
import com.bank.auth.exception.UserNotFoundException;
import com.bank.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User users = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());

        List<GrantedAuthority> authorities = users.getRoles()
                .stream()
                .map(UserRole::getRole)
                .map(Role::getName)
                .map(name -> (GrantedAuthority) new SimpleGrantedAuthority(name))
                .toList();

        return new AppUserDetails(
                users.getId().toString(),
                users.getUsername(),
                users.getPasswordHash(),
                authorities
        );
    }
}