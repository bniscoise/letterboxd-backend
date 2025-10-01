package com.example.letterboxdbackend.dto;

import com.example.letterboxdbackend.model.UserMovie;

public class UserMovieDto {
    // attributs
    private Long movieId;
    private String movieTitle;
    private Double rating;
    private String review;

    public UserMovieDto() {
    }

    public UserMovieDto(UserMovie userMovie) {
        this.movieId = userMovie.getMovie().getId();
        String primaryTitle = userMovie.getMovie().getPrimaryTitle();
        String originalTitle = userMovie.getMovie().getOriginalTitle();
        this.movieTitle = (primaryTitle == null || primaryTitle.isBlank()) ? originalTitle : primaryTitle;
        this.rating = userMovie.getRating();
        this.review = userMovie.getReview();
    }

    // Getters et Setters

    public Long getMovieId() {
        return this.movieId;
    }

    public void setMovieId(Long movieid) {
        this.movieId = movieid;
    }

    public String getMovieTitle() {
        return this.movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public Double getRating() {
        return this.rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getReview() {
        return this.review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
