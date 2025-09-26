package com.example.letterboxdbackend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class UserMovieUpdateRequestDto {

    @Min(0)
    @Max(5)
    private Integer rating;

    @Size(max = 2000)
    private String review;

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
