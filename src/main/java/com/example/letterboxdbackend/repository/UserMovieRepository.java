package com.example.letterboxdbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.letterboxdbackend.model.Movie;
import com.example.letterboxdbackend.model.User;
import com.example.letterboxdbackend.model.UserMovie;
import com.example.letterboxdbackend.repository.projection.MovieRatingProjection;

public interface UserMovieRepository extends JpaRepository<UserMovie, Long> {
    List<UserMovie> findByUser(User user);

    Optional<UserMovie> findByUserAndMovie(User user, Movie movie);

    List<UserMovie> findByMovie(Movie movie);

    @EntityGraph(attributePaths = { "movie", "user" })
    List<UserMovie> findByRatingIsNotNullOrderByUpdatedAtDesc(Pageable pageable);

    @Query("""
            SELECT m.id as movieId,
                   m.primaryTitle as primaryTitle,
                   m.originalTitle as originalTitle,
                   m.posterUrl as posterUrl,
                   AVG(um.rating) as averageRating,
                   COUNT(um.id) as ratingCount
            FROM UserMovie um
            JOIN um.movie m
            WHERE um.rating IS NOT NULL
            GROUP BY m.id, m.primaryTitle, m.originalTitle, m.posterUrl
            HAVING COUNT(um.id) > 0
            ORDER BY AVG(um.rating) DESC, COUNT(um.id) DESC
            """)
    List<MovieRatingProjection> findTopRatedMovies(Pageable pageable);
}
