package com.example.letterboxdbackend.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.letterboxdbackend.dto.RecentRatingDto;
import com.example.letterboxdbackend.dto.TopRatedMovieDto;
import com.example.letterboxdbackend.repository.UserMovieRepository;

@Service
public class TrendingService {

    private static final int DEFAULT_LIMIT = 5;

    private final UserMovieRepository userMovieRepository;

    public TrendingService(UserMovieRepository userMovieRepository) {
        this.userMovieRepository = userMovieRepository;
    }

    public List<RecentRatingDto> getLatestRatings() {
        return userMovieRepository
                .findByRatingIsNotNullOrderByUpdatedAtDesc(PageRequest.of(0, DEFAULT_LIMIT))
                .stream()
                .map(RecentRatingDto::new)
                .toList();
    }

    public List<TopRatedMovieDto> getTopRatedMovies() {
        return userMovieRepository
                .findTopRatedMovies(PageRequest.of(0, DEFAULT_LIMIT))
                .stream()
                .map(TopRatedMovieDto::new)
                .toList();
    }
}
