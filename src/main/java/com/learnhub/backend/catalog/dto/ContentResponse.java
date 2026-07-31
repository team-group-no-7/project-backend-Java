package com.learnhub.backend.catalog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ContentResponse {

    private Long id;
    private String title;
    private String description;

    @JsonProperty("preview_text")
    private String previewText;

    @JsonProperty("content_body")
    private String contentBody;

    @JsonProperty("file_url")
    private String fileUrl;

    private BigDecimal price;
    private String type;
    private String level;
    private String tags;
    private String status;
    private Boolean featured;

    @JsonProperty("is_trending")
    private Boolean isTrending;

    private BigDecimal rating;

    @JsonProperty("reviews_count")
    private Integer reviewsCount;

    @JsonProperty("learners_count")
    private Integer learnersCount;

    @JsonProperty("approval_status")
    private String approvalStatus;

    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("creator_id")
    private Long creatorId;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("creator_name")
    private String creatorName;

    @JsonProperty("creator_avatar")
    private String creatorAvatar;

    public ContentResponse() {
    }

    // Constructor matching CreatorContentServiceImpl's call
    public ContentResponse(Long id, String title, String description, String previewText, String contentBody, String fileUrl, BigDecimal price, String type, String level, String tags, String status, Boolean featured, Boolean isTrending, BigDecimal rating, Integer reviewsCount, Integer learnersCount, Long categoryId, Long creatorId, LocalDateTime createdAt) {
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

    public Boolean getTrending() {
        return isTrending;
    }

    public void setTrending(Boolean trending) {
        this.isTrending = trending;
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

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorAvatar() {
        return creatorAvatar;
    }

    public void setCreatorAvatar(String creatorAvatar) {
        this.creatorAvatar = creatorAvatar;
    }
}
