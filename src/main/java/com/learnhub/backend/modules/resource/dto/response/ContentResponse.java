package com.learnhub.backend.modules.resource.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.learnhub.backend.modules.resource.enums.ContentType;

import java.math.BigDecimal;

/**
 * ContentResponse DTO — Pure Java DTO without Lombok annotations.
 */
public class ContentResponse {

    private Long id;
    private String title;
    private String description;
    private String previewText;
    private String fileUrl;
    private BigDecimal price;
    private ContentType type;
    private String level;
    private String tags;
    private Boolean featured;
    private Boolean trending;
    private BigDecimal rating;
    private Integer reviewsCount;
    private Integer learnersCount;
    private String categoryName;
    private String creatorName;

    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;

    // Default Constructor
    public ContentResponse() {
    }

    // Parameterized Constructor
    public ContentResponse(Long id, String title, String description, String previewText, String fileUrl, BigDecimal price, ContentType type, String level, String tags, Boolean featured, Boolean trending, BigDecimal rating, Integer reviewsCount, Integer learnersCount, String categoryName, String creatorName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.previewText = previewText;
        this.fileUrl = fileUrl;
        this.price = price;
        this.type = type;
        this.level = level;
        this.tags = tags;
        this.featured = featured;
        this.trending = trending;
        this.rating = rating;
        this.reviewsCount = reviewsCount;
        this.learnersCount = learnersCount;
        this.categoryName = categoryName;
        this.creatorName = creatorName;
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

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ContentType getType() {
        return type;
    }

    public void setType(ContentType type) {
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
        return trending;
    }

    public void setTrending(Boolean trending) {
        this.trending = trending;
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String toString() {
        return "ContentResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                '}';
    }
}