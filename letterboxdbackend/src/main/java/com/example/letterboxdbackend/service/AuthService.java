package com.example.letterboxdbackend.service;

import com.example.letterboxdbackend.model.User;
import com.example.letterboxdbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String username, String email, String password) {
        userRepository.findByUsername(username).ifPresent(user -> {
            throw new IllegalArgumentException("username already in use");
        });

        userRepository.findByEmail(email).ifPresent(user -> {
            throw new IllegalArgumentException("email already in use");
        });

        User user = new User(username, email, password);
        return userRepository.save(user);
    }

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("invalid credentials"));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("invalid credentials");
        }

        return user;
    }
}
