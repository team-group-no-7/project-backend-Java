package com.learnhub.backend.catalog.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Category Entity — Maps to the CATEGORIES table in the database.
 * Used to classify technical learning materials.
 */
@Entity
@Table(name = "categories")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "resource_count")
    private Integer resourceCount = 0;
}
