package com.example.letterboxdbackend.migration;

import com.example.letterboxdbackend.model.User;
import com.example.letterboxdbackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("password-migration")
public class UserPasswordMigrationRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserPasswordMigrationRunner.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserPasswordMigrationRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        List<User> users = userRepository.findAll();
        List<User> updatedUsers = new ArrayList<>();

        for (User user : users) {
            String currentPassword = user.getPassword();

            if (currentPassword == null || currentPassword.isBlank()) {
                continue;
            }

            if (isAlreadyEncoded(currentPassword)) {
                continue;
            }

            user.setPassword(passwordEncoder.encode(currentPassword));
            updatedUsers.add(user);
        }

        if (!updatedUsers.isEmpty()) {
            userRepository.saveAll(updatedUsers);
        }

        log.info("Password migration completed: {} password(s) updated", updatedUsers.size());
    }

    private boolean isAlreadyEncoded(String value) {
        return value.startsWith("{");
    }
}
