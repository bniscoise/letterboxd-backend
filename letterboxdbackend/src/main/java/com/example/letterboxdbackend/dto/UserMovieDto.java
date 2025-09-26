package com.example.letterboxdbackend.dto;

import com.example.letterboxdbackend.model.UserMovie;

public class UserMovieDto {
    private Long id;
    private Long movieId;
    private String imdbId;
    private String title;
    private Integer year;
    private Integer rating;
    private String review;

    public UserMovieDto() {
    }

    public UserMovieDto(UserMovie userMovie) {
        this.id = userMovie.getId();
        this.movieId = userMovie.getMovie().getId();
        this.imdbId = userMovie.getMovie().getImdbId();
        this.title = userMovie.getMovie().getPrimaryTitle();
        this.year = userMovie.getMovie().getStartYear();
        this.rating = userMovie.getRating();
        this.review = userMovie.getReview();
    }

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return this.movieId;
    }

    public void setMovieId(Long movieid) {
        this.movieId = movieid;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getRating() {
        return this.rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return this.review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
