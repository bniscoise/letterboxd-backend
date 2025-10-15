package com.example.letterboxdbackend.dto;

import com.example.letterboxdbackend.repository.projection.MovieRatingProjection;

public class TopRatedMovieDto {

    private Long movieId;
    private String title;
    private String posterUrl;
    private Double averageRating;
    private Long ratingCount;

    public TopRatedMovieDto() {
    }

    public TopRatedMovieDto(MovieRatingProjection projection) {
        this.movieId = projection.getMovieId();
        this.title = resolveTitle(projection);
        this.posterUrl = projection.getPosterUrl();
        this.averageRating = projection.getAverageRating();
        this.ratingCount = projection.getRatingCount();
    }

    private String resolveTitle(MovieRatingProjection projection) {
        String primary = projection.getPrimaryTitle();
        String original = projection.getOriginalTitle();
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

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Long getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Long ratingCount) {
        this.ratingCount = ratingCount;
    }
}
