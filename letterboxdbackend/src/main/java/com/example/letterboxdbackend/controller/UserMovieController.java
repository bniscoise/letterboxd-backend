package com.example.letterboxdbackend.controller;

import com.example.letterboxdbackend.dto.UserMovieDto;
import com.example.letterboxdbackend.dto.UserMovieRequestDto;
import com.example.letterboxdbackend.dto.UserMovieUpdateRequestDto;
import com.example.letterboxdbackend.model.UserMovie;
import com.example.letterboxdbackend.service.UserMovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/{userId}/movies")
public class UserMovieController {

    private final UserMovieService userMovieService;

    public UserMovieController(UserMovieService userMovieService) {
        this.userMovieService = userMovieService;
    }

    @PostMapping
    public ResponseEntity<UserMovieDto> addMovieToUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserMovieRequestDto request) {

        UserMovie userMovie = userMovieService.saveOrUpdateUserMovie(userId, request.getMovieId(),
                request.getRating(), request.getReview());
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserMovieDto(userMovie));
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<UserMovieDto> updateUserMovie(
            @PathVariable Long userId,
            @PathVariable Long movieId,
            @Valid @RequestBody UserMovieUpdateRequestDto request) {

        return userMovieService.getUserMovie(userId, movieId)
                .map(userMovie -> userMovieService.saveOrUpdateUserMovie(userId, movieId, request.getRating(),
                        request.getReview()))
                .map(UserMovieDto::new)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserMovieDto>> getUserMovies(@PathVariable Long userId) {
        List<UserMovieDto> dtos = userMovieService.getMoviesForUser(userId)
                .stream()
                .map(UserMovieDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<UserMovieDto> getUserMovie(@PathVariable Long userId, @PathVariable Long movieId) {
        return userMovieService.getUserMovie(userId, movieId)
                .map(UserMovieDto::new)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteUserMovie(@PathVariable Long userId, @PathVariable Long movieId) {
        if (userMovieService.getUserMovie(userId, movieId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        userMovieService.removeMovieForUser(userId, movieId);
        return ResponseEntity.noContent().build();
    }
}
