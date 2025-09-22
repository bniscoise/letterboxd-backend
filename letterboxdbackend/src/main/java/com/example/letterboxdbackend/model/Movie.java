package com.example.letterboxdbackend.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "movies")
public class Movie {
    // attributs

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    @NotBlank
    private String imdbId; 
    
    @Column(nullable = false)
    @NotBlank
    private String primaryTitle;
    
    private String originalTitle;
    
    @Column(columnDefinition = "TEXT")
    private String overview;
    
    @NotNull
    private Integer startYear;
    
    private Integer endYear;
    
    @Column(nullable = false)
    @NotBlank
    private String type;
    
    private String posterUrl;
    
    private Double aggregateRating;
    
    private Long voteCount;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    //Constructeurs
    public Movie(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Movie(String imdbId, String primaryTitle, String originalTitle, Integer startYear, String type){
        this();
        this.imdbId = imdbId;
        this.primaryTitle = primaryTitle;
        this.originalTitle = originalTitle;
        this.startYear = startYear;
        this.type = type;
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
    
    public String getPrimaryTitle() {
        return primaryTitle;
    }
    
    public void setPrimaryTitle(String primaryTitle) {
        this.primaryTitle = primaryTitle;
    }
    
    public String getOriginalTitle() {
        return originalTitle;
    }
    
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }
    
    public String getOverview() {
        return overview;
    }
    
    public void setOverview(String overview) {
        this.overview = overview;
    }
    
    public Integer getStartYear() {
        return startYear;
    }
    
    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }
    
    public Integer getEndYear() {
        return endYear;
    }
    
    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", imdbId='" + imdbId + '\'' +
                ", primaryTitle='" + primaryTitle + '\'' +
                ", startYear=" + startYear +
                ", type='" + type + '\'' +
                '}';
    }
}
