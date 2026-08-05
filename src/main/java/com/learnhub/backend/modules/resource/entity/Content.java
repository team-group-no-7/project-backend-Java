package com.learnhub.backend.modules.resource.entity;

import com.learnhub.backend.modules.user.entity.User;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price = BigDecimal.ZERO;

    private String type;
    private String level;
    private String tags;

    @Column(name = "status")
    private String status = "PUBLISHED"; // DRAFT or PUBLISHED

    private Boolean featured = false;

    @Column(name = "is_trending")
    private Boolean isTrending = false;

    @Column(precision = 3, scale = 2)
    private BigDecimal rating = BigDecimal.ZERO;

    @Column(name = "reviews_count")
    private Integer reviewsCount = 0;

    @Column(name = "learners_count")
    private Integer learnersCount = 0;

    @Column(name = "approval_status")
    private String approvalStatus = "APPROVED"; // PENDING, APPROVED, FLAGGED

    @Column(name = "category_id")
    private Long categoryId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;

    @Column(name = "creator_id")
    private Long creatorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id", insertable = false, updatable = false)
    private User creator;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Content() {
    }

    public Content(Long id, String title, String description, String previewText, String contentBody, String fileUrl, BigDecimal price, String type, String level, String tags, String status, Boolean featured, Boolean isTrending, BigDecimal rating, Integer reviewsCount, Integer learnersCount, String approvalStatus, Category category, User creator, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.previewText = previewText;
        this.contentBody = contentBody;
        this.fileUrl = fileUrl;
        this.price = price;
        this.type = type;
        this.level = level;
        this.status = status;
        this.featured = featured;
        this.isTrending = isTrending;
        this.rating = rating;
        this.reviewsCount = reviewsCount;
        this.learnersCount = learnersCount;
        this.approvalStatus = approvalStatus;
        this.category = category;
        if (category != null) {
            this.categoryId = category.getId();
        }
        this.creator = creator;
        if (creator != null) {
            this.creatorId = creator.getId();
        }
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPreviewText() { return previewText; }
    public void setPreviewText(String previewText) { this.previewText = previewText; }

    public String getContentBody() { return contentBody; }
    public void setContentBody(String contentBody) { this.contentBody = contentBody; }

    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }

    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Boolean getFeatured() { return featured; }
    public Boolean isFeatured() { return featured != null && featured; }
    public void setFeatured(Boolean featured) { this.featured = featured; }

    public Boolean getTrending() { return isTrending; }
    public Boolean getIsTrending() { return isTrending; }
    public Boolean isTrending() { return isTrending != null && isTrending; }
    public void setIsTrending(Boolean isTrending) { this.isTrending = isTrending; }

    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }

    public Integer getReviewsCount() { return reviewsCount; }
    public void setReviewsCount(Integer reviewsCount) { this.reviewsCount = reviewsCount; }

    public Integer getLearnersCount() { return learnersCount; }
    public void setLearnersCount(Integer learnersCount) { this.learnersCount = learnersCount; }

    public String getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(String approvalStatus) { this.approvalStatus = approvalStatus; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { 
        this.category = category; 
        if (category != null) {
            this.categoryId = category.getId();
        }
    }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public User getCreator() { return creator; }
    public void setCreator(User creator) { 
        this.creator = creator; 
        if (creator != null) {
            this.creatorId = creator.getId();
        }
    }

    public Long getCreatorId() { return creatorId; }
    public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return id != null && id.equals(content.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
