package com.learnhub.backend.modules.resource.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * ResourceDetailResponse — Comprehensive DTO for single resource page view.
 * Matches React frontend ResourceDetailPage.jsx expectations (camelCase & snake_case aliases).
 * Implemented in pure Java without Lombok.
 */
public class ResourceDetailResponse {

    private Long id;
    private String title;
    private String description;
    private String previewText;
    private String contentBody;
    private String fileUrl;
    private BigDecimal price;
    private String type;
    private String level;
    private List<String> tags;
    private BigDecimal rating;
    private Integer reviewsCount;
    private Integer learnersCount;
    private Long categoryId;
    private String categoryName;
    private Long creatorId;
    private String creatorName;
    private String creatorAvatar;
    private String thumbnailUrl;
    private String createdAt;
    private List<ReviewDto> reviews;

    public ResourceDetailResponse() {
    }

    public ResourceDetailResponse(Long id, String title, String description, String previewText,
                                  String contentBody, String fileUrl, BigDecimal price, String type,
                                  String level, List<String> tags, BigDecimal rating, Integer reviewsCount,
                                  Integer learnersCount, Long categoryId, String categoryName,
                                  Long creatorId, String creatorName, String creatorAvatar,
                                  String createdAt, List<ReviewDto> reviews) {
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
        this.rating = rating;
        this.reviewsCount = reviewsCount;
        this.learnersCount = learnersCount;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.creatorAvatar = creatorAvatar;
        this.createdAt = createdAt;
        this.reviews = reviews;
    }

    // Standard Getters & Setters
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

    public String getPreview_text() {
        return previewText;
    }

    public String getContentBody() {
        return contentBody;
    }

    public void setContentBody(String contentBody) {
        this.contentBody = contentBody;
    }

    public String getContent_body() {
        return contentBody;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFile_url() {
        return fileUrl;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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

    public Integer getReviews_count() {
        return reviewsCount;
    }

    public Integer getLearnersCount() {
        return learnersCount;
    }

    public void setLearnersCount(Integer learnersCount) {
        this.learnersCount = learnersCount;
    }

    public Integer getLearners_count() {
        return learnersCount;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCategory_id() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategory_name() {
        return categoryName;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getCreator_id() {
        return creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreator_name() {
        return creatorName;
    }

    public String getCreatorAvatar() {
        return creatorAvatar;
    }

    public void setCreatorAvatar(String creatorAvatar) {
        this.creatorAvatar = creatorAvatar;
    }

    public String getCreator_avatar() {
        return creatorAvatar;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getThumbnail_url() {
        return thumbnailUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreated_at() {
        return createdAt;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }
}
