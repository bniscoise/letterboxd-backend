package com.example.letterboxdbackend.repository;

import com.example.letterboxdbackend.model.UserMovie;
import com.example.letterboxdbackend.model.User;
import com.example.letterboxdbackend.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserMovieRepository extends JpaRepository<UserMovie, Long> {
    List<UserMovie> findByUser(User user);

    Optional<UserMovie> findByUserAndMovie(User user, Movie movie);

    List<UserMovie> findByMovie(Movie movie);
}
