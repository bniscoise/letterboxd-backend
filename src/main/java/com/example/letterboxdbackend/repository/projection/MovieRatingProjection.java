package com.example.letterboxdbackend.repository.projection;

public interface MovieRatingProjection {
    Long getMovieId();

    String getPrimaryTitle();

    String getOriginalTitle();

    String getPosterUrl();

    Double getAverageRating();

    Long getRatingCount();
}
