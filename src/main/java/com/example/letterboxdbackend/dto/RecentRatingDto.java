package com.example.letterboxdbackend.dto;

import java.time.LocalDateTime;

import com.example.letterboxdbackend.model.UserMovie;

public class RecentRatingDto {

    private Long movieId;
    private String title;
    private String posterUrl;
    private Double rating;
    private LocalDateTime ratedAt;
    private String ratedBy;

    public RecentRatingDto() {
    }

    public RecentRatingDto(UserMovie userMovie) {
        this.movieId = userMovie.getMovie().getId();
        this.title = resolveTitle(userMovie);
        this.posterUrl = userMovie.getMovie().getPosterUrl();
        this.rating = userMovie.getRating();
        this.ratedAt = userMovie.getUpdatedAt();
        this.ratedBy = userMovie.getUser().getUsername();
    }

    private String resolveTitle(UserMovie userMovie) {
        String primary = userMovie.getMovie().getPrimaryTitle();
        String original = userMovie.getMovie().getOriginalTitle();
        if (primary != null && !primary.isBlank()) {
            return primary;
        }
        return original != null ? original : "";
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDateTime getRatedAt() {
        return ratedAt;
    }

    public void setRatedAt(LocalDateTime ratedAt) {
        this.ratedAt = ratedAt;
    }

    public String getRatedBy() {
        return ratedBy;
    }

    public void setRatedBy(String ratedBy) {
        this.ratedBy = ratedBy;
    }
}
