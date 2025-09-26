package com.example.letterboxdbackend.service;

import com.example.letterboxdbackend.model.Movie;
import com.example.letterboxdbackend.model.User;
import com.example.letterboxdbackend.model.UserMovie;
import com.example.letterboxdbackend.repository.MovieRepository;
import com.example.letterboxdbackend.repository.UserMovieRepository;
import com.example.letterboxdbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<UserMovie> getMoviesForUser(Long userId) {
        User user = getUser(userId);
        return userMovieRepository.findByUser(user);
    }

    public Optional<UserMovie> getUserMovie(Long userId, Long movieId) {
        User user = getUser(userId);
        Movie movie = getMovie(movieId);
        return userMovieRepository.findByUserAndMovie(user, movie);
    }

    @Transactional
    public UserMovie saveOrUpdateUserMovie(Long userId, Long movieId, Integer rating, String review) {
        User user = getUser(userId);
        Movie movie = getMovie(movieId);

        UserMovie userMovie = userMovieRepository.findByUserAndMovie(user, movie)
                .orElseGet(() -> {
                    UserMovie created = new UserMovie();
                    created.setUser(user);
                    created.setMovie(movie);
                    return created;
                });

        if (rating != null) {
            userMovie.setRating(rating);
        }

        userMovie.setReview(review);

        return userMovieRepository.save(userMovie);
    }

    @Transactional
    public void removeMovieForUser(Long userId, Long movieId) {
        User user = getUser(userId);
        Movie movie = getMovie(movieId);
        userMovieRepository.deleteByUserAndMovie(user, movie);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));
    }

    private Movie getMovie(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Film introuvable"));
    }
}
