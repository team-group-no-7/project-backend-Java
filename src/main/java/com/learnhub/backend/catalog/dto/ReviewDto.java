package com.learnhub.backend.catalog.dto;

/**
 * ReviewDto — DTO representing learner reviews for a resource.
 * Implemented in pure Java without Lombok.
 */
public class ReviewDto {

    private Long id;
    private String studentName;
    private String avatar;
    private Integer rating;
    private String comment;
    private String date;

    public ReviewDto() {
    }

    public ReviewDto(Long id, String studentName, String avatar, Integer rating, String comment, String date) {
        this.id = id;
        this.studentName = studentName;
        this.avatar = avatar;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar_url() {
        return avatar;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReview_text() {
        return comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReview_date() {
        return date;
    }
}
