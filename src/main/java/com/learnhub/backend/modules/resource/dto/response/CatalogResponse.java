package com.learnhub.backend.modules.resource.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * CatalogResponse DTO — Updated to match all frontend catalog card mappings.
 */
public class CatalogResponse {

    private Long id;
    private String title;
    private String description;
    private String type;

    @JsonProperty("category_name")
    private String category;

    private BigDecimal price;

    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;

    @JsonProperty("creator_name")
    private String creatorName;

    @JsonProperty("creator_avatar")
    private String creatorAvatar;

    @JsonProperty("creator_id")
    private Long creatorId;

    @JsonProperty("is_trending")
    private Boolean isTrending;

    private Boolean featured;
    private Double rating;

    @JsonProperty("reviews_count")
    private Integer reviewsCount;

    @JsonProperty("learners_count")
    private Integer learnersCount;

    // Default Constructor
    public CatalogResponse() {
    }

    // Parameterized Constructor
    public CatalogResponse(Long id, String title, String description, String type, String category, 
                           BigDecimal price, String thumbnailUrl, String creatorName, String creatorAvatar, 
                           Long creatorId, Boolean isTrending, Boolean featured, Double rating, 
                           Integer reviewsCount, Integer learnersCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.category = category;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
        this.creatorName = creatorName;
        this.creatorAvatar = creatorAvatar;
        this.creatorId = creatorId;
        this.isTrending = isTrending;
        this.featured = featured;
        this.rating = rating;
        this.reviewsCount = reviewsCount;
        this.learnersCount = learnersCount;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    public String getCreatorName() { return creatorName; }
    public void setCreatorName(String creatorName) { this.creatorName = creatorName; }

    public String getCreatorAvatar() { return creatorAvatar; }
    public void setCreatorAvatar(String creatorAvatar) { this.creatorAvatar = creatorAvatar; }

    public Long getCreatorId() { return creatorId; }
    public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }

    public Boolean getIsTrending() { return isTrending; }
    public void setIsTrending(Boolean isTrending) { this.isTrending = isTrending; }

    public Boolean getFeatured() { return featured; }
    public void setFeatured(Boolean featured) { this.featured = featured; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public Integer getReviewsCount() { return reviewsCount; }
    public void setReviewsCount(Integer reviewsCount) { this.reviewsCount = reviewsCount; }

    public Integer getLearnersCount() { return learnersCount; }
    public void setLearnersCount(Integer learnersCount) { this.learnersCount = learnersCount; }
}