package com.example.letterboxdbackend.service;

import com.example.letterboxdbackend.dto.ImdbApiResponse;
import com.example.letterboxdbackend.dto.MovieDto;
import com.example.letterboxdbackend.model.Movie;
import com.example.letterboxdbackend.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);
    private static final String IMDB_API_BASE_URL = "https://api.imdbapi.dev/search/titles";

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * A function to turn the imdb movies into a Movie object
     * 
     * @param imdbTitle
     * @return movie object
     */
    private Movie convertToMovie(ImdbApiResponse.ImdbTitle imdbTitle) {
        Movie movie = new Movie();
        movie.setImdbId(imdbTitle.getId());
        movie.setPrimaryTitle(imdbTitle.getPrimaryTitle());
        movie.setOriginalTitle(imdbTitle.getOriginalTitle());
        movie.setStartYear(imdbTitle.getStartYear());
        movie.setEndYear(imdbTitle.getEndYear());
        movie.setType(imdbTitle.getType());

        // Poster URL
        if (imdbTitle.getPrimaryImage() != null) {
            movie.setPosterUrl(imdbTitle.getPrimaryImage().getUrl());
        }

        // Rating
        if (imdbTitle.getRating() != null) {
            movie.setAggregateRating(imdbTitle.getRating().getAggregateRating());
            movie.setVoteCount(imdbTitle.getRating().getVoteCount());
        }

        return movie;
    }

    /**
     * function to call the IMDB free API and save movies to the local database
     * 
     * @param query
     * @return list of movies
     */
    private List<MovieDto> searchFromImdbAndSave(String query) {
        try {
            logger.info("Calling IMDB API for {}", query);
            String url = IMDB_API_BASE_URL + "?query=" + query;

            ImdbApiResponse response = restTemplate.getForObject(url, ImdbApiResponse.class);

            if (response == null || response.getTitles() == null) {
                logger.warn("404 movie not found");
                return List.of();
            }

            List<Movie> movies = response.getTitles().stream()
                    .filter(title -> "movie".equals(title.getType())).map(this::convertToMovie)
                    .filter(movie -> !movieRepository.existsByImdbId(movie.getImdbId()))
                    .map(movieRepository::save).collect(Collectors.toList());

            logger.info("Saved {} movies", movies.size());

            return movies.stream().map(MovieDto::new).collect(Collectors.toList());
        } catch (RestClientException exception) {
            logger.error("ERROR: {}", exception.getMessage());
            return List.of();
        }
    }

    /**
     * Search in the local Database if the movie is already here, if not calls the
     * imdb API
     * 
     * @param query
     * @return list of movies
     */
    public List<MovieDto> searchMovies(String query) {
        logger.info("Search for movies: {}", query);

        List<Movie> localMovies = movieRepository.searchMoviesByTitle(query);

        if (!localMovies.isEmpty()) {
            logger.info("found {} locally", localMovies.size());
            return localMovies.stream()
                    .map(MovieDto::new)
                    .collect(Collectors.toList());
        }
        return searchFromImdbAndSave(query);

    }
}
