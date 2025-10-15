package com.example.letterboxdbackend.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.letterboxdbackend.dto.RecentRatingDto;
import com.example.letterboxdbackend.dto.TopRatedMovieDto;
import com.example.letterboxdbackend.model.Movie;
import com.example.letterboxdbackend.model.User;
import com.example.letterboxdbackend.model.UserMovie;
import com.example.letterboxdbackend.repository.MovieRepository;
import com.example.letterboxdbackend.repository.UserMovieRepository;
import com.example.letterboxdbackend.repository.UserRepository;

@DataJpaTest
class TrendingServiceTest {

    @Autowired
    private UserMovieRepository userMovieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    private TrendingService trendingService;

    @BeforeEach
    void setUp() {
        userMovieRepository.deleteAll();
        userRepository.deleteAll();
        movieRepository.deleteAll();
        trendingService = new TrendingService(userMovieRepository);
    }

    @Test
    void getLatestRatingsShouldReturnMostRecentEntries() throws InterruptedException {
        User user = userRepository.save(new User("latest-user", "latest@example.com", "pwd"));
        Movie movie = movieRepository.save(buildMovie("tt000001", "Latest Movie"));

        for (int i = 0; i < 6; i++) {
            UserMovie userMovie = new UserMovie(user, movie, 3.0 + i, null);
            userMovieRepository.save(userMovie);
            // ensure updatedAt differs
            Thread.sleep(5);
        }

        List<RecentRatingDto> latest = trendingService.getLatestRatings();

        assertThat(latest).hasSize(5);
        assertThat(latest.get(0).getRating()).isGreaterThanOrEqualTo(latest.get(1).getRating());
        assertThat(latest.get(0).getRatedAt()).isAfterOrEqualTo(latest.get(1).getRatedAt());
    }

    @Test
    void getTopRatedMoviesShouldOrderByAverageRating() {
        User user1 = userRepository.save(new User("user1", "user1@example.com", "pwd"));
        User user2 = userRepository.save(new User("user2", "user2@example.com", "pwd"));
        User user3 = userRepository.save(new User("user3", "user3@example.com", "pwd"));

        Movie movieA = movieRepository.save(buildMovie("tt111111", "Movie A"));
        Movie movieB = movieRepository.save(buildMovie("tt222222", "Movie B"));

        userMovieRepository.save(new UserMovie(user1, movieA, 5.0, null));
        userMovieRepository.save(new UserMovie(user2, movieA, 4.0, null));

        userMovieRepository.save(new UserMovie(user1, movieB, 3.0, null));
        userMovieRepository.save(new UserMovie(user3, movieB, 3.5, null));

        List<TopRatedMovieDto> top = trendingService.getTopRatedMovies();

        assertThat(top).isNotEmpty();
        assertThat(top.get(0).getMovieId()).isEqualTo(movieA.getId());
        assertThat(top.get(0).getAverageRating()).isGreaterThan(top.get(1).getAverageRating());
    }

    private Movie buildMovie(String imdbId, String title) {
        Movie movie = new Movie();
        movie.setImdbId(imdbId);
        movie.setPrimaryTitle(title);
        movie.setType("movie");
        return movie;
    }
}
