package com.example.letterboxdbackend.controller;

import com.example.letterboxdbackend.dto.UserMovieDto;
import com.example.letterboxdbackend.model.UserMovie;
import com.example.letterboxdbackend.service.UserMovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
            @RequestParam(required = false) Integer rating,
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
}
