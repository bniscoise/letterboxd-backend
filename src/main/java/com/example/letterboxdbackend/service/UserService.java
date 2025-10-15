package com.example.letterboxdbackend.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.example.letterboxdbackend.model.User;
import com.example.letterboxdbackend.model.UserFollow;
import com.example.letterboxdbackend.repository.UserFollowRepository;
import com.example.letterboxdbackend.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;

    public UserService(UserRepository userRepository, UserFollowRepository userFollowRepository) {
        this.userRepository = userRepository;
        this.userFollowRepository = userFollowRepository;
    }

    public User createUser(String username, String email) {
        return createUser(username, email, "");
    }

    public User createUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("unknown user"));
    }

    public List<User> searchUsers(String query) {
        if (query == null) {
            return List.of();
        }

        String trimmed = query.trim();
        if (trimmed.length() < 2) {
            return List.of();
        }

        return userRepository.findTop10ByUsernameContainingIgnoreCaseOrderByUsernameAsc(trimmed);
    }

    public User followUser(Long followerId, Long targetId) {
        if (Objects.equals(followerId, targetId)) {
            throw new IllegalArgumentException("Impossible de se suivre soi-même.");
        }

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable."));

        User target = userRepository.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable."));

        boolean alreadyFollowing = userFollowRepository.existsByFollowerIdAndFollowedId(followerId, targetId);
        if (!alreadyFollowing) {
            UserFollow relation = new UserFollow(follower, target);
            userFollowRepository.save(relation);
        }

        return target;
    }

    public void unfollowUser(Long followerId, Long targetId) {
        if (Objects.equals(followerId, targetId)) {
            return;
        }

        userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable."));
        userRepository.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable."));

        userFollowRepository.findByFollowerIdAndFollowedId(followerId, targetId)
                .ifPresent(userFollowRepository::delete);
    }

    public List<User> getFollowingUsers(Long followerId) {
        userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable."));
        return userFollowRepository.findFollowedUsers(followerId);
    }
}
