package com.example.letterboxdbackend.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    @NotBlank
    private String imdbId; // ex: "tt4154796"
    
    @Column(nullable = false)
    @NotBlank
    private String primaryTitle;
    
    private String originalTitle;
    
    @Column(columnDefinition = "TEXT")
    private String overview;
    
    @NotNull
    private Integer startYear;
    
    private Integer endYear; // pour les séries TV
    
    @Column(nullable = false)
    @NotBlank
    private String type; // "movie", "tvSeries", "tvSpecial"
    
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
}
