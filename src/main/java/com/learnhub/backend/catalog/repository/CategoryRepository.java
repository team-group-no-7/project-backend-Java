package com.learnhub.backend.catalog.repository;

import com.learnhub.backend.catalog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CategoryRepository — Data access interface for Category entity.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
