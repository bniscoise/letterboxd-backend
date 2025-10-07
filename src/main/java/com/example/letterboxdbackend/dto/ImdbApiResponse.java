package com.example.letterboxdbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ImdbApiResponse {

    @JsonProperty("titles")
    private List<ImdbTitle> titles;

    public ImdbApiResponse() {
    }

    public List<ImdbTitle> getTitles() {
        return titles;
    }

    public void setTitles(List<ImdbTitle> titles) {
        this.titles = titles;
    }

    // Classe interne pour représenter chaque titre
    public static class ImdbTitle {
        @JsonProperty("id")
        private String id;

        @JsonProperty("type")
        private String type;

        @JsonProperty("primaryTitle")
        private String primaryTitle;

        @JsonProperty("originalTitle")
        private String originalTitle;

        @JsonProperty("startYear")
        private Integer startYear;

        @JsonProperty("endYear")
        private Integer endYear;

        @JsonProperty("primaryImage")
        private PrimaryImage primaryImage;

        @JsonProperty("rating")
        private Rating rating;

        @JsonProperty("plot")
        private String plot;

        // Constructeurs
        public ImdbTitle() {
        }

        // Getters et Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public PrimaryImage getPrimaryImage() {
            return primaryImage;
        }

        public void setPrimaryImage(PrimaryImage primaryImage) {
            this.primaryImage = primaryImage;
        }

        public Rating getRating() {
            return rating;
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public String getPlot() {
            return this.plot;
        }

        public void setPlot(String plot) {
            this.plot = plot;
        }
    }

    public static class PrimaryImage {
        @JsonProperty("url")
        private String url;

        @JsonProperty("width")
        private Integer width;

        @JsonProperty("height")
        private Integer height;

        public PrimaryImage() {
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }
    }

    public static class Rating {
        @JsonProperty("aggregateRating")
        private Double aggregateRating;

        @JsonProperty("voteCount")
        private Long voteCount;

        public Rating() {
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
}