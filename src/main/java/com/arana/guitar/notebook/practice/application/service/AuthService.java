package com.arana.guitar.notebook.practice.application.service;

import com.arana.guitar.notebook.practice.domain.models.User;
import com.arana.guitar.notebook.practice.domain.objects.PasswordHash;
import com.arana.guitar.notebook.practice.domain.repo.UserRepository;
import com.arana.guitar.notebook.practice.domain.util.PasswordService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public AuthService(UserRepository userRepository,
                       PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public User authenticate(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->  new BadCredentialsException("Invalid credentials"));

        PasswordHash stored = new PasswordHash(
                user.getPasswordHash(),
                user.getPasswordSalt(),
                user.getPasswordIterations()
        );

        if (!passwordService.matches(rawPassword, stored)) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }
}