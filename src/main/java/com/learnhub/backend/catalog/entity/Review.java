package com.learnhub.backend.catalog.entity;

import jakarta.persistence.*;

/**
 * Review Entity — Maps to the REVIEWS table in PostgreSQL.
 * Implemented in pure Java without Lombok annotations.
 */
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "avatar_url")
    private String avatarUrl;

    private Integer rating;

    @Column(name = "review_text", columnDefinition = "TEXT")
    private String reviewText;

    @Column(name = "review_date")
    private String reviewDate;

    @Column(name = "content_id")
    private Long contentId;

    @Column(name = "session_id")
    private Long sessionId;

    public Review() {
    }

    public Review(Long id, Long userId, String studentName, String avatarUrl, Integer rating,
                  String reviewText, String reviewDate, Long contentId, Long sessionId) {
        this.id = id;
        this.userId = userId;
        this.studentName = studentName;
        this.avatarUrl = avatarUrl;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
        this.contentId = contentId;
        this.sessionId = sessionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", studentName='" + studentName + '\'' +
                ", rating=" + rating +
                ", contentId=" + contentId +
                '}';
    }
}
