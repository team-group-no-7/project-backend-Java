package com.learnhub.backend.modules.resource.dto.response;

/**
 * ReviewResponse — DTO representing a content review.
 * Prevents raw JPA Review entity exposure.
 */
public class ReviewResponse {

    private Long id;
    private Long contentId;
    private String studentName;
    private String avatarUrl;
    private Integer rating;
    private String reviewDate;
    private String comment;

    public ReviewResponse() {
    }

    public ReviewResponse(Long id, Long contentId, String studentName, String avatarUrl, Integer rating, String reviewDate, String comment) {
        this.id = id;
        this.contentId = contentId;
        this.studentName = studentName;
        this.avatarUrl = avatarUrl;
        this.rating = rating;
        this.reviewDate = reviewDate;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
