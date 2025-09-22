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
    
    @Query("SELECT m FROM Movie m WHERE " +
           "(LOWER(m.primaryTitle) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(m.originalTitle) LIKE LOWER(CONCAT('%', :query, '%'))) " +
           "AND m.type = 'movie' " +
           "ORDER BY m.aggregateRating DESC NULLS LAST")
    List<Movie> searchMoviesByTitle(@Param("query") String query);
    
    @Query("SELECT m FROM Movie m WHERE m.type = 'movie' AND m.aggregateRating IS NOT NULL " +
           "ORDER BY m.aggregateRating DESC, m.voteCount DESC")
    List<Movie> findTopRatedMovies();
    
    boolean existsByImdbId(String imdbId);

}
