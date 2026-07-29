package com.learnhub.backend.catalog.repository;

import com.learnhub.backend.catalog.entity.Content;
import com.learnhub.backend.catalog.enums.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {

    List<Content> findByTitleContainingIgnoreCase(String keyword);

    List<Content> findByCategoryId(Long categoryId);

    List<Content> findByType(ContentType type);
}