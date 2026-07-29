package com.learnhub.backend.catalog.repository;

import com.learnhub.backend.catalog.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ContentRepository — Data access interface for Content entity.
 * Provides custom JPQL queries for creator analytics, resource management grid, and catalog search.
 */
@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    /**
     * Find all contents created by a specific creator.
     */
    List<Content> findByCreatorId(Long creatorId);

    /**
     * Count total resources created by a specific creator.
     */
    long countByCreatorId(Long creatorId);

    /**
     * Calculate total learners enrolled across all resources created by this creator.
     * Uses COALESCE to return 0 if creator has no resources or no enrolled learners.
     */
    @Query("SELECT COALESCE(SUM(c.learnersCount), 0) FROM Content c WHERE c.creatorId = :creatorId")
    long sumLearnersCountByCreatorId(@Param("creatorId") Long creatorId);

    /**
     * Calculate total revenue earned by creator (sum of price * learnersCount for each resource).
     * Uses COALESCE to return 0.0 if creator has no earnings.
     */
    @Query("SELECT COALESCE(SUM(c.price * c.learnersCount), 0.0) FROM Content c WHERE c.creatorId = :creatorId")
    Double calculateTotalEarningsByCreatorId(@Param("creatorId") Long creatorId);
}
