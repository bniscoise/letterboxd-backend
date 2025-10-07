package com.example.letterboxdbackend.repository;

import com.example.letterboxdbackend.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
       Optional<Movie> findByImdbId(String imdbId);

       List<Movie> findByPrimaryTitleContainingIgnoreCaseAndType(String title, String type);

       List<Movie> findByTypeOrderByAggregateRatingDesc(String type);

       List<Movie> findByStartYearBetween(Integer startYear, Integer endYear);

       @Query("SELECT movie FROM Movie movie WHERE " +
                     "(LOWER(movie.primaryTitle) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
                     "LOWER(movie.originalTitle) LIKE LOWER(CONCAT('%', :query, '%'))) " +
                     "AND movie.type = 'movie' " +
                     "ORDER BY movie.aggregateRating DESC NULLS LAST")
       List<Movie> searchMoviesByTitle(@Param("query") String query);

       @Query("SELECT movie FROM Movie movie WHERE movie.type = 'movie' AND movie.aggregateRating IS NOT NULL " +
                     "ORDER BY movie.aggregateRating DESC, movie.voteCount DESC")
       List<Movie> findTopRatedMovies();

    boolean existsByImdbId(String imdbId);
}
