package com.learnhub.backend.catalog.entity;

import jakarta.persistence.*;

/*
 * Category Entity — Maps to the CATEGORIES table in the database.
 */
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "resource_count")
    private Integer resourceCount = 0;

    // Default Constructor (Required by JPA)
    public Category() {
    }

    // Parameterized Constructor
    public Category(Long id, String name, Integer resourceCount) {
        this.id = id;
        this.name = name;
        this.resourceCount = resourceCount;
    }

    public Category(String name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(Integer resourceCount) {
        this.resourceCount = resourceCount;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", resourceCount=" + resourceCount +
                '}';
    }
}
