package com.learnhub.backend.modules.resource.controller;

import com.learnhub.backend.common.dto.ApiResponse;
import com.learnhub.backend.modules.resource.dto.response.ReviewResponse;
import com.learnhub.backend.modules.resource.entity.Content;
import com.learnhub.backend.modules.resource.entity.Review;
import com.learnhub.backend.modules.resource.repository.ContentRepository;
import com.learnhub.backend.modules.resource.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ReviewController — REST Controller for Content Reviews using ReviewResponse DTOs.
 */
@RestController
@RequestMapping("/api/contents")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final ContentRepository contentRepository;

    public ReviewController(ReviewRepository reviewRepository, ContentRepository contentRepository) {
        this.reviewRepository = reviewRepository;
        this.contentRepository = contentRepository;
    }

    @GetMapping("/{contentId}/reviews")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviews(@PathVariable Long contentId) {
        List<Review> reviews = reviewRepository.findByContentId(contentId);
        List<ReviewResponse> dtoList = reviews.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success("Reviews fetched successfully", dtoList));
    }

    @PostMapping("/{contentId}/reviews")
    public ResponseEntity<ApiResponse<ReviewResponse>> addReview(
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

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Review submitted successfully", mapToResponse(savedReview)));
    }

    private ReviewResponse mapToResponse(Review r) {
        return new ReviewResponse(
                r.getId(),
                r.getContentId(),
                r.getStudentName(),
                r.getAvatarUrl(),
                r.getRating(),
                r.getReviewDate(),
                r.getReviewText()
        );
    }
}
