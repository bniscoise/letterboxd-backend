package com.example.letterboxdbackend.controller;

import com.example.letterboxdbackend.dto.UserMovieDto;
import com.example.letterboxdbackend.model.UserMovie;
import com.example.letterboxdbackend.service.UserMovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/user-movies")
public class UserMovieController {

    private final UserMovieService userMovieService;

    public UserMovieController(UserMovieService userMovieService) {
        this.userMovieService = userMovieService;
    }

    @PostMapping("/{userId}/{movieId}")
    public ResponseEntity<UserMovieDto> addOrUpdateMovie(
            @PathVariable Long userId,
            @PathVariable Long movieId,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) String review) {

        UserMovie userMovie = userMovieService.addOrUpdateMovieForUser(userId, movieId, rating, review);
        return ResponseEntity.ok(new UserMovieDto(userMovie));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserMovieDto>> getUserMovies(@PathVariable Long userId) {
        List<UserMovieDto> dtos = userMovieService.getMoviesForUser(userId)
                .stream()
                .map(UserMovieDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<UserMovieDto>> getReviewsForMovie(@PathVariable Long movieId) {
        List<UserMovieDto> dtos = userMovieService.getReviewsForMovie(movieId)
                .stream()
                .map(UserMovieDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{userId}/{movieId}")
    public ResponseEntity<UserMovieDto> deleteUserMovie(@PathVariable Long userId, @PathVariable Long movieId) {
        userMovieService.deleteMovieFromUserList(userId, movieId);
        return ResponseEntity.noContent().build();
    }

}