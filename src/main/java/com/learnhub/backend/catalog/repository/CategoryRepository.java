package com.learnhub.backend.catalog.repository;

import com.learnhub.backend.catalog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * CategoryRepository — Data access interface for Category entity.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Find category by name (case-insensitive or exact match)
    Optional<Category> findByName(String name);
}
