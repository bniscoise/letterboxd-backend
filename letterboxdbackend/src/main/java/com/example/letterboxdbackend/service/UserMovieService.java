package com.example.letterboxdbackend.service;

import com.example.letterboxdbackend.model.Movie;
import com.example.letterboxdbackend.model.User;
import com.example.letterboxdbackend.model.UserMovie;
import com.example.letterboxdbackend.repository.MovieRepository;
import com.example.letterboxdbackend.repository.UserMovieRepository;
import com.example.letterboxdbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service permettant d'ajouter de noter et de critiquer des films dans la liste
 * des utilisateurs.
 * 
 */
@Service
public class UserMovieService {

    // attributs
    private final UserMovieRepository userMovieRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    // Constructeur
    public UserMovieService(UserMovieRepository userMovieRepository,
            MovieRepository movieRepository,
            UserRepository userRepository) {
        this.userMovieRepository = userMovieRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    /**
     * Associe un film à un utilisateur
     * Ajoute aussi une note et une critique
     * 
     * @param userId
     * @param movieId
     * @param rating
     * @param review
     * @return
     */
    public UserMovie addOrUpdateMovieForUser(Long userId, Long movieId, Double rating, String review) {
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

    /**
     * 
     * @param userId
     * @return la liste des films d'un utilisateur
     */
    public List<UserMovie> getMoviesForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        return userMovieRepository.findByUser(user);
    }
}
