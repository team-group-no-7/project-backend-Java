package com.learnhub.backend.catalog.entity;

import com.learnhub.backend.catalog.enums.ContentType;
import com.learnhub.backend.user.entity.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Content Entity — Maps to CONTENTS table.
 * Implemented in pure Java without Lombok annotations.
 */
@Entity
@Table(name = "contents")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "preview_text", columnDefinition = "TEXT")
    private String previewText;

    @Column(name = "content_body", columnDefinition = "TEXT")
    private String contentBody;

    @Column(name = "file_url")
    private String fileUrl;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ContentType type;

    private String level;

    private String tags;

    private Boolean featured;

    @Column(name = "is_trending")
    private Boolean trending;

    private BigDecimal rating;

    @Column(name = "reviews_count")
    private Integer reviewsCount;

    @Column(name = "learners_count")
    private Integer learnersCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "category_id", insertable = false, updatable = false)
    private Long categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(name = "creator_id", insertable = false, updatable = false)
    private Long creatorId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Default Constructor (Required by JPA)
    public Content() {
    }

    // Parameterized Constructor
    public Content(Long id, String title, String description, String previewText, String contentBody, String fileUrl, BigDecimal price, ContentType type, String level, String tags, Boolean featured, Boolean trending, BigDecimal rating, Integer reviewsCount, Integer learnersCount, Category category, User creator, LocalDateTime createdAt) {
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
        this.trending = trending;
        this.rating = rating;
        this.reviewsCount = reviewsCount;
        this.learnersCount = learnersCount;
        this.category = category;
        this.creator = creator;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getCategoryId() {
        return categoryId != null ? categoryId : (category != null ? category.getId() : null);
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Long getCreatorId() {
        return creatorId != null ? creatorId : (creator != null ? creator.getId() : null);
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
                ", type=" + type +
                '}';
    }
}
