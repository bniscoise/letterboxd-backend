package com.example.letterboxdbackend.service;

import com.example.letterboxdbackend.model.Movie;
import com.example.letterboxdbackend.model.User;
import com.example.letterboxdbackend.model.UserMovie;
import com.example.letterboxdbackend.repository.MovieRepository;
import com.example.letterboxdbackend.repository.UserMovieRepository;
import com.example.letterboxdbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMovieService {

    private final UserMovieRepository userMovieRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public UserMovieService(UserMovieRepository userMovieRepository,
            MovieRepository movieRepository,
            UserRepository userRepository) {
        this.userMovieRepository = userMovieRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public UserMovie addOrUpdateMovieForUser(Long userId, Long movieId, Integer rating, String review) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Film introuvable"));

        UserMovie userMovie = userMovieRepository.findByUserAndMovie(user, movie)
                .orElse(new UserMovie());

        if (userMovie.getId() == null) {
            userMovie.setUser(user);
            userMovie.setMovie(movie);
        }

        userMovie.setRating(rating);
        userMovie.setReview(review);

        return userMovieRepository.save(userMovie);
    }

    public List<UserMovie> getMoviesForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        return userMovieRepository.findByUser(user);
    }
}
