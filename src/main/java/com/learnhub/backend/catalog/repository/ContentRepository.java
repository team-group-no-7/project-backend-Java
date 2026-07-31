package com.learnhub.backend.catalog.repository;

import com.learnhub.backend.catalog.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    List<Content> findByCreatorId(Long creatorId);

    long countByCreatorId(Long creatorId);

    @Query("SELECT COALESCE(SUM(c.learnersCount), 0) FROM Content c WHERE c.creatorId = :creatorId")
    long sumLearnersCountByCreatorId(@Param("creatorId") Long creatorId);

    @Query("SELECT COALESCE(SUM(c.price * c.learnersCount), 0.0) FROM Content c WHERE c.creatorId = :creatorId")
    Double calculateTotalEarningsByCreatorId(@Param("creatorId") Long creatorId);

    @Query("SELECT c FROM Content c WHERE " +
           "(cast(:search as string) IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', cast(:search as string), '%'))) AND " +
           "(cast(:categoryName as string) IS NULL OR cast(:categoryName as string) = 'All' OR c.category.name = cast(:categoryName as string))")
    List<Content> searchAndFilter(
        @Param("search") String search,
        @Param("categoryName") String categoryName
    );

    List<Content> findByTitleContainingIgnoreCase(String keyword);

    List<Content> findByCategoryId(Long categoryId);

    List<Content> findByType(String type);

    List<Content> findByFeaturedTrue();

    List<Content> findByIsTrendingTrue();

    List<Content> findTop6ByOrderByRatingDesc();

    List<Content> findTop10ByOrderByLearnersCountDesc();
}
