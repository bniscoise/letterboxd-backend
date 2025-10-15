package com.example.letterboxdbackend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.letterboxdbackend.dto.UserSummaryDto;
import com.example.letterboxdbackend.model.User;
import com.example.letterboxdbackend.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = { "http://127.0.0.1:4200", "http://localhost:4200" })
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestParam String username, @RequestParam String email) {
        return ResponseEntity.ok(userService.createUser(username, email));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSummaryDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(new UserSummaryDto(userService.getUserById(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserSummaryDto>> searchUsers(@RequestParam("query") String query) {
        List<UserSummaryDto> users = userService.searchUsers(query).stream()
                .map(UserSummaryDto::new)
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<List<UserSummaryDto>> getFollowing(@PathVariable("id") Long userId) {
        List<UserSummaryDto> following = userService.getFollowingUsers(userId).stream()
                .map(UserSummaryDto::new)
                .toList();
        return ResponseEntity.ok(following);
    }

    @PostMapping("/{id}/follow/{targetId}")
    public ResponseEntity<UserSummaryDto> followUser(@PathVariable("id") Long userId,
            @PathVariable("targetId") Long targetId) {
        User followed = userService.followUser(userId, targetId);
        return ResponseEntity.ok(new UserSummaryDto(followed));
    }

    @DeleteMapping("/{id}/follow/{targetId}")
    public ResponseEntity<Void> unfollowUser(@PathVariable("id") Long userId,
            @PathVariable("targetId") Long targetId) {
        userService.unfollowUser(userId, targetId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", exception.getMessage()));
    }
}
