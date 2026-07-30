package com.learnhub.backend.auth.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * User Entity — Maps to USERS table.
 * Implemented in pure Java without Lombok annotations.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(nullable = false, unique = true)
    private String email;

    private String headline;

    private String location;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    private String role;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    // Default Constructor (Required by JPA)
    public User() {
    }

    // Parameterized Constructor
    public User(Long id, String avatarUrl, String email, String headline, String location, String name, String password, String role, LocalDateTime joinedAt) {
        this.id = id;
        this.avatarUrl = avatarUrl;
        this.email = email;
        this.headline = headline;
        this.location = location;
        this.name = name;
        this.password = password;
        this.role = role;
        this.joinedAt = joinedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}