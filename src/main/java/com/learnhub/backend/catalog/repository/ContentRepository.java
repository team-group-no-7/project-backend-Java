package com.learnhub.backend.catalog.repository;

import com.learnhub.backend.catalog.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    // Filter by Creator ID (used in Creator Dashboard Management Grid)
    List<Content> findByCreatorId(Long creatorId);

    // Dynamic search and filter query
    @Query("SELECT c FROM Content c WHERE " +
           "(cast(:search as string) IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', cast(:search as string), '%'))) AND " +
           "(cast(:categoryName as string) IS NULL OR cast(:categoryName as string) = 'All' OR c.category.name = cast(:categoryName as string))")
    List<Content> searchAndFilter(
        @Param("search") String search,
        @Param("categoryName") String categoryName
    );
}
