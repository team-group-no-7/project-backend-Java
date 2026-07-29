package com.learnhub.backend.catalog.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Content Entity — Maps to the CONTENTS table in the database.
 * Represents learning resources (courses, articles, video/PDF materials) created by creators.
 *
 * Implemented in pure Java with explicit getters, setters, and constructors (no Lombok).
 */
@Entity
@Table(name = "contents")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "preview_text", columnDefinition = "TEXT")
    private String previewText;

    @Column(name = "content_body", columnDefinition = "TEXT")
    private String contentBody;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(precision = 10, scale = 2)
    private Double price = 0.00;

    private String type;

    private String level;

    private String tags;

    private Boolean featured = false;

    @Column(name = "is_trending")
    private Boolean isTrending = false;

    @Column(precision = 3, scale = 2)
    private Double rating = 0.00;

    @Column(name = "reviews_count")
    private Integer reviewsCount = 0;

    @Column(name = "learners_count")
    private Integer learnersCount = 0;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "creator_id")
    private Long creatorId;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    // Default Constructor (Required by JPA/Hibernate)
    public Content() {
    }

    // Full Parameterized Constructor
    public Content(Long id, String title, String description, String previewText, String contentBody, String fileUrl, Double price, String type, String level, String tags, Boolean featured, Boolean isTrending, Double rating, Integer reviewsCount, Integer learnersCount, Long categoryId, Long creatorId, LocalDateTime createdAt) {
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
        this.featured = featured;
        this.isTrending = isTrending;
        this.rating = rating;
        this.reviewsCount = reviewsCount;
        this.learnersCount = learnersCount;
        this.categoryId = categoryId;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
    }

    // Convenient Constructor for creating new Content
    public Content(String title, String description, Double price, String type, String level, Long categoryId, Long creatorId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.type = type;
        this.level = level;
        this.categoryId = categoryId;
        this.creatorId = creatorId;
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
        return "Content{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", creatorId=" + creatorId +
                ", learnersCount=" + learnersCount +
                '}';
    }
}
