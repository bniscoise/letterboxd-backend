package com.example.letterboxdbackend.service;

import com.example.letterboxdbackend.model.User;
import com.example.letterboxdbackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String email, String password) {
        userRepository.findByUsername(username).ifPresent(user -> {
            throw new IllegalArgumentException("username already in use");
        });

        userRepository.findByEmail(email).ifPresent(user -> {
            throw new IllegalArgumentException("email already in use");
        });

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, email, encodedPassword);
        return userRepository.save(user);
    }

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("invalid credentials");
        }

        return user;
    }
}
