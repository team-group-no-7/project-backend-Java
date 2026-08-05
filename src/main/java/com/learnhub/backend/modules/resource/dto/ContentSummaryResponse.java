package com.learnhub.backend.modules.resource.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ContentSummaryResponse — DTO representing content cards on public pages.
 * Implemented in pure Java without Lombok.
 */
public class ContentSummaryResponse {

    private Long id;
    private String title;
    private String description;
    private String previewText;
    private BigDecimal price;
    private String type;
    private String level;
    private String tags;
    private Boolean featured;
    private Boolean isTrending;
    private BigDecimal rating;
    private Integer reviewsCount;
    private Integer learnersCount;
    private String categoryName;
    private Long creatorId;
    private String creatorName;
    private String creatorAvatarUrl;
    private LocalDateTime createdAt;

    public ContentSummaryResponse() {
    }

    public ContentSummaryResponse(Long id, String title, String description, String previewText,
                                  BigDecimal price, String type, String level, String tags,
                                  Boolean featured, Boolean isTrending, BigDecimal rating,
                                  Integer reviewsCount, Integer learnersCount, String categoryName,
                                  Long creatorId, String creatorName, String creatorAvatarUrl,
                                  LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.previewText = previewText;
        this.price = price;
        this.type = type;
        this.level = level;
        this.tags = tags;
        this.featured = featured;
        this.isTrending = isTrending;
        this.rating = rating;
        this.reviewsCount = reviewsCount;
        this.learnersCount = learnersCount;
        this.categoryName = categoryName;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.creatorAvatarUrl = creatorAvatarUrl;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreviewText() {
        return previewText;
    }

    public void setPreviewText(String previewText) {
        this.previewText = previewText;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Boolean getTrending() {
        return isTrending;
    }

    public void setTrending(Boolean trending) {
        isTrending = trending;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Integer getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(Integer reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public Integer getLearnersCount() {
        return learnersCount;
    }

    public void setLearnersCount(Integer learnersCount) {
        this.learnersCount = learnersCount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorAvatarUrl() {
        return creatorAvatarUrl;
    }

    public void setCreatorAvatarUrl(String creatorAvatarUrl) {
        this.creatorAvatarUrl = creatorAvatarUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
