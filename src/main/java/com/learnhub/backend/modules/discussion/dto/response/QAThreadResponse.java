package com.learnhub.backend.modules.discussion.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * QAThreadResponse — DTO representing a discussion thread and nested replies.
 * Prevents raw JPA QAThread entity exposure.
 */
public class QAThreadResponse {

    private Long id;
    private Long contentId;
    private String authorName;
    private String role;
    private String question;
    private Integer upvotes;
    private Boolean isResolved;
    private LocalDateTime createdAt;
    private List<QAReplyResponse> replies = new ArrayList<>();

    public QAThreadResponse() {
    }

    public QAThreadResponse(Long id, Long contentId, String authorName, String role, String question, Integer upvotes, Boolean isResolved, LocalDateTime createdAt, List<QAReplyResponse> replies) {
        this.id = id;
        this.contentId = contentId;
        this.authorName = authorName;
        this.role = role;
        this.question = question;
        this.upvotes = upvotes;
        this.isResolved = isResolved;
        this.createdAt = createdAt;
        if (replies != null) {
            this.replies = replies;
        }
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public Boolean getIsResolved() {
        return isResolved;
    }

    public void setIsResolved(Boolean resolved) {
        isResolved = resolved;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<QAReplyResponse> getReplies() {
        return replies;
    }

    public void setReplies(List<QAReplyResponse> replies) {
        this.replies = replies;
    }
}
