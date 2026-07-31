package com.learnhub.backend.catalog.controller;

import com.learnhub.backend.catalog.entity.Content;
import com.learnhub.backend.catalog.entity.Review;
import com.learnhub.backend.catalog.repository.ContentRepository;
import com.learnhub.backend.catalog.repository.ReviewRepository;
import com.learnhub.backend.common.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/contents")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ContentRepository contentRepository;

    @GetMapping("/{contentId}/reviews")
    public ResponseEntity<ApiResponse<List<Review>>> getReviews(@PathVariable Long contentId) {
        List<Review> reviews = reviewRepository.findByContentId(contentId);
        return ResponseEntity.ok(ApiResponse.success("Reviews fetched successfully", reviews));
    }

    @PostMapping("/{contentId}/reviews")
    public ResponseEntity<ApiResponse<Review>> addReview(
            @PathVariable Long contentId,
            @RequestBody Review review) {

        review.setContentId(contentId);
        if (review.getReviewDate() == null) {
            review.setReviewDate(LocalDate.now().toString());
        }
        if (review.getStudentName() == null || review.getStudentName().isEmpty()) {
            review.setStudentName("Learner");
        }
        if (review.getAvatarUrl() == null) {
            review.setAvatarUrl("https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150");
        }

        Review savedReview = reviewRepository.save(review);

        // Update Content average rating and review count
        Content content = contentRepository.findById(contentId).orElse(null);
        if (content != null) {
            List<Review> allReviews = reviewRepository.findByContentId(contentId);
            int count = allReviews.size();
            double avgRating = allReviews.stream().mapToInt(r -> r.getRating() != null ? r.getRating() : 5).average().orElse(5.0);

            content.setReviewsCount(count);
            content.setRating(BigDecimal.valueOf(avgRating).setScale(1, RoundingMode.HALF_UP));
            contentRepository.save(content);
        }

        return ResponseEntity.ok(ApiResponse.success("Review submitted successfully", savedReview));
    }
}
