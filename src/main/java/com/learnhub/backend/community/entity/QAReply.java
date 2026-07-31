package com.learnhub.backend.community.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "qa_replies")
public class QAReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "thread_id", insertable = false, updatable = false)
    private Long threadId;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    private String role; // LEARNER, CREATOR, ADMIN

    @Column(length = 2000, nullable = false)
    private String reply;

    private Integer upvotes = 1;

    @Column(name = "is_verified_answer")
    private Boolean isVerifiedAnswer = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public QAReply() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getThreadId() { return threadId; }
    public void setThreadId(Long threadId) { this.threadId = threadId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getReply() { return reply; }
    public void setReply(String reply) { this.reply = reply; }

    public Integer getUpvotes() { return upvotes; }
    public void setUpvotes(Integer upvotes) { this.upvotes = upvotes; }

    public Boolean getIsVerifiedAnswer() { return isVerifiedAnswer; }
    public void setIsVerifiedAnswer(Boolean verifiedAnswer) { isVerifiedAnswer = verifiedAnswer; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
