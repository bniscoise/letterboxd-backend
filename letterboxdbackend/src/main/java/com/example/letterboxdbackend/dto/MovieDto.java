package com.example.letterboxdbackend.dto;

import com.example.letterboxdbackend.model.Movie;
import java.time.LocalDateTime;

public class MovieDto {
    // Attributs
    private Long id;
    private String imdbId;
    private String primaryTitle;
    private String originalTitle;
    private String overview;
    private Integer startYear;
    private Integer endYear;
    private String type;
    private String posterUrl;
    private Double aggregateRating;
    private Long voteCount;
    private LocalDateTime createdAt;
    
    public MovieDto() {}
    
    // Constructeur
    public MovieDto(Movie movie) {
        this.id = movie.getId();
        this.imdbId = movie.getImdbId();
        this.primaryTitle = movie.getPrimaryTitle();
        this.originalTitle = movie.getOriginalTitle();
        this.overview = movie.getOverview();
        this.startYear = movie.getStartYear();
        this.endYear = movie.getEndYear();
        this.type = movie.getType();
        this.posterUrl = movie.getPosterUrl();
        this.aggregateRating = movie.getAggregateRating();
        this.voteCount = movie.getVoteCount();
        this.createdAt = movie.getCreatedAt();
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getImdbId() { return imdbId; }
    public void setImdbId(String imdbId) { this.imdbId = imdbId; }
    
    public String getPrimaryTitle() { return primaryTitle; }
    public void setPrimaryTitle(String primaryTitle) { this.primaryTitle = primaryTitle; }
    
    public String getOriginalTitle() { return originalTitle; }
    public void setOriginalTitle(String originalTitle) { this.originalTitle = originalTitle; }
    
    public String getOverview() { return overview; }
    public void setOverview(String overview) { this.overview = overview; }
    
    public Integer getStartYear() { return startYear; }
    public void setStartYear(Integer startYear) { this.startYear = startYear; }
    
    public Integer getEndYear() { return endYear; }
    public void setEndYear(Integer endYear) { this.endYear = endYear; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
    
    public Double getAggregateRating() { return aggregateRating; }
    public void setAggregateRating(Double aggregateRating) { this.aggregateRating = aggregateRating; }
    
    public Long getVoteCount() { return voteCount; }
    public void setVoteCount(Long voteCount) { this.voteCount = voteCount; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}