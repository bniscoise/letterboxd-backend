package com.example.letterboxdbackend.controller;

import com.example.letterboxdbackend.model.User;
import com.example.letterboxdbackend.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

/**
 * Classe gérant toute l'authentification.
 * 
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = authService.register(request.username(), request.email(), request.password());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(buildResponse(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = authService.authenticate(request.username(), request.password());
        return ResponseEntity.ok(buildResponse(user));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }

    private AuthResponse buildResponse(User user) {
        String token = UUID.randomUUID().toString();
        return new AuthResponse(user.getId(), user.getUsername(), user.getEmail(), token);
    }

    public record RegisterRequest(
            @NotBlank String username,
            @Email(message = "invalid email") String email,
            @NotBlank String password) {
    }

    public record LoginRequest(
            @NotBlank String username,
            @NotBlank String password) {
    }

    public record AuthResponse(Long id, String username, String email, String token) {
    }

}
