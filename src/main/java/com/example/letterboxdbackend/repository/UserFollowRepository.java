package com.example.letterboxdbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.letterboxdbackend.model.User;
import com.example.letterboxdbackend.model.UserFollow;

public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {

    boolean existsByFollowerIdAndFollowedId(Long followerId, Long followedId);

    Optional<UserFollow> findByFollowerIdAndFollowedId(Long followerId, Long followedId);

    void deleteByFollowerIdAndFollowedId(Long followerId, Long followedId);

    @Query("select uf.followed from UserFollow uf where uf.follower.id = :followerId order by lower(uf.followed.username)")
    List<User> findFollowedUsers(@Param("followerId") Long followerId);
}
