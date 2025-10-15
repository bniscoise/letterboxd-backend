package com.example.letterboxdbackend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.letterboxdbackend.model.User;
import com.example.letterboxdbackend.repository.UserFollowRepository;
import com.example.letterboxdbackend.repository.UserRepository;

@DataJpaTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFollowRepository userFollowRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userFollowRepository.deleteAll();
        userRepository.deleteAll();
        userService = new UserService(userRepository, userFollowRepository);
    }

    @Test
    void followUserShouldCreateRelation() {
        User follower = userRepository.save(new User("alice", "alice@example.com", "pwd"));
        User followed = userRepository.save(new User("bob", "bob@example.com", "pwd"));

        User result = userService.followUser(follower.getId(), followed.getId());

        assertThat(result.getId()).isEqualTo(followed.getId());
        assertThat(userFollowRepository.existsByFollowerIdAndFollowedId(follower.getId(), followed.getId()))
                .isTrue();
    }

    @Test
    void followUserShouldRejectSelfFollow() {
        User follower = userRepository.save(new User("charlie", "charlie@example.com", "pwd"));

        assertThatThrownBy(() -> userService.followUser(follower.getId(), follower.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Impossible de se suivre");
    }

    @Test
    void unfollowUserShouldRemoveRelation() {
        User follower = userRepository.save(new User("david", "david@example.com", "pwd"));
        User followed = userRepository.save(new User("eric", "eric@example.com", "pwd"));

        userService.followUser(follower.getId(), followed.getId());
        assertThat(userFollowRepository.existsByFollowerIdAndFollowedId(follower.getId(), followed.getId()))
                .isTrue();

        userService.unfollowUser(follower.getId(), followed.getId());

        assertThat(userFollowRepository.existsByFollowerIdAndFollowedId(follower.getId(), followed.getId()))
                .isFalse();
    }

    @Test
    void searchUsersShouldMatchByUsername() {
        userRepository.save(new User("amelie", "amelie@example.com", "pwd"));
        userRepository.save(new User("bernard", "bernard@example.com", "pwd"));
        userRepository.save(new User("amelia", "amelia@example.com", "pwd"));

        List<User> results = userService.searchUsers("ame");

        assertThat(results)
                .extracting(User::getUsername)
                .containsExactlyInAnyOrder("amelie", "amelia");
    }
}
