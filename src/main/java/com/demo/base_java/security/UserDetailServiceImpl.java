package com.demo.base_java.security;

import com.demo.base_java.exception.EntityNotFoundException;
import com.demo.base_java.model.User;
import com.demo.base_java.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (new EmailValidator().isValid(username, null)) {
            return userRepository
                    .findByEmail(username)
                    .map(this::createUserSecurity)
                    .orElseThrow(() -> new EntityNotFoundException(User.class.getName(), username));
        }

        return userRepository
                .findByUsername(username)
                .map(this::createUserSecurity)
                .orElseThrow(() -> new EntityNotFoundException(User.class.getName(), username));

    }

    private org.springframework.security.core.userdetails.User createUserSecurity(User user) {
        Set<GrantedAuthority> securityAuthorities = user
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), securityAuthorities);
    }
}
