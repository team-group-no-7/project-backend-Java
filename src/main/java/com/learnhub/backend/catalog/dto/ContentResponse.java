package com.learnhub.backend.catalog.dto;

import java.time.LocalDateTime;

/**
 * ContentResponse — DTO returning detailed content information after creation/fetch.
 *
 * Implemented in pure Java with explicit getters, setters, and constructors (no Lombok).
 */
public class ContentResponse {

    private Long id;
    private String title;
    private String description;
    private String previewText;
    private String contentBody;
    private String fileUrl;
    private Double price;
    private String type;
    private String level;
    private String tags;
    private String status;
    private Boolean featured;
    private Boolean isTrending;
    private Double rating;
    private Integer reviewsCount;
    private Integer learnersCount;
    private Long categoryId;
    private Long creatorId;
    private LocalDateTime createdAt;

    // Default Constructor (Required by Jackson for JSON serialization)
    public ContentResponse() {
    }

    // Full Parameterized Constructor
    public ContentResponse(Long id, String title, String description, String previewText, String contentBody, String fileUrl, Double price, String type, String level, String tags, String status, Boolean featured, Boolean isTrending, Double rating, Integer reviewsCount, Integer learnersCount, Long categoryId, Long creatorId, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.previewText = previewText;
        this.contentBody = contentBody;
        this.fileUrl = fileUrl;
        this.price = price;
        this.type = type;
        this.level = level;
        this.tags = tags;
        this.status = status;
        this.featured = featured;
        this.isTrending = isTrending;
        this.rating = rating;
        this.reviewsCount = reviewsCount;
        this.learnersCount = learnersCount;
        this.categoryId = categoryId;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
    }

    // Getters and Setters
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

    public String getContentBody() {
        return contentBody;
    }

    public void setContentBody(String contentBody) {
        this.contentBody = contentBody;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Boolean getIsTrending() {
        return isTrending;
    }

    public void setIsTrending(Boolean isTrending) {
        this.isTrending = isTrending;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ContentResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", creatorId=" + creatorId +
                '}';
    }
}
