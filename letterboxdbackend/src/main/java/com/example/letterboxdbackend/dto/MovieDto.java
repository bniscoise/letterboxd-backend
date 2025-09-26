package com.example.letterboxdbackend.dto;

import com.example.letterboxdbackend.model.Movie;

public class MovieDto {
    private Long id;
    private String imdbId;
    private String title;
    private Integer year;
    private String posterUrl;
    private Double aggregateRating;
    private Long voteCount;

    public MovieDto() {
    }

    // Constructeur
    public MovieDto(Movie movie) {
        this.id = movie.getId();
        this.imdbId = movie.getImdbId();
        this.title = movie.getPrimaryTitle();
        this.year = movie.getStartYear();
        this.posterUrl = movie.getPosterUrl();
        this.aggregateRating = movie.getAggregateRating();
        this.voteCount = movie.getVoteCount();
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTitle() {
        return title;
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

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Double getAggregateRating() {
        return aggregateRating;
    }

    public void setAggregateRating(Double aggregateRating) {
        this.aggregateRating = aggregateRating;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }
}
