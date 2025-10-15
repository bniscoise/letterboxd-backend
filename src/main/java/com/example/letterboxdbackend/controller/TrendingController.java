package com.example.letterboxdbackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.letterboxdbackend.dto.RecentRatingDto;
import com.example.letterboxdbackend.dto.TopRatedMovieDto;
import com.example.letterboxdbackend.service.TrendingService;

@RestController
@RequestMapping("/api/trending")
@CrossOrigin(origins = { "http://127.0.0.1:4200", "http://localhost:4200" })
public class TrendingController {

    private final TrendingService trendingService;

    public TrendingController(TrendingService trendingService) {
        this.trendingService = trendingService;
    }

    @GetMapping("/latest")
    public ResponseEntity<List<RecentRatingDto>> getLatestRatings() {
        return ResponseEntity.ok(trendingService.getLatestRatings());
    }

    @GetMapping("/top")
    public ResponseEntity<List<TopRatedMovieDto>> getTopRatedMovies() {
        return ResponseEntity.ok(trendingService.getTopRatedMovies());
    }
}
