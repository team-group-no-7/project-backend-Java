package com.learnhub.backend.modules.resource.repository;

import com.learnhub.backend.modules.resource.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ReviewRepository — Spring Data JPA repository for Review entity.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByContentId(Long contentId);
}
