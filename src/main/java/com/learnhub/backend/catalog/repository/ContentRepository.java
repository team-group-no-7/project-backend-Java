package com.learnhub.backend.catalog.repository;

import com.learnhub.backend.catalog.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ContentRepository — Spring Data JPA Repository for Content entity.
 * Supports querying featured, trending, top-rated contents, and category filters.
 */
@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    List<Content> findByFeaturedTrue();

    List<Content> findByIsTrendingTrue();

    List<Content> findTop6ByOrderByRatingDesc();

    List<Content> findTop10ByOrderByLearnersCountDesc();

    List<Content> findByCreatorId(Long creatorId);

    @Query("SELECT c FROM Content c WHERE " +
           "(:search IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(c.tags) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
           "(:category IS NULL OR LOWER(c.category.name) = LOWER(:category))")
    List<Content> searchAndFilter(@Param("search") String search, @Param("category") String category);
}
