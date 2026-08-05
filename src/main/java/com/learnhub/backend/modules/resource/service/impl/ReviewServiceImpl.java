package com.learnhub.backend.modules.resource.service.impl;

import com.learnhub.backend.common.exception.BadRequestException;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.common.util.SecurityUtils;
import com.learnhub.backend.modules.payment.repository.PurchaseRepository;
import com.learnhub.backend.modules.resource.dto.response.ReviewResponse;
import com.learnhub.backend.modules.resource.entity.Content;
import com.learnhub.backend.modules.resource.entity.Review;
import com.learnhub.backend.modules.resource.repository.ContentRepository;
import com.learnhub.backend.modules.resource.repository.ReviewRepository;
import com.learnhub.backend.modules.resource.service.ReviewService;
import com.learnhub.backend.modules.user.entity.User;
import com.learnhub.backend.modules.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ReviewServiceImpl — Implementation class for Content Reviews encapsulating business rules.
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    private static final Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository reviewRepository;
    private final ContentRepository contentRepository;
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ContentRepository contentRepository,
                             PurchaseRepository purchaseRepository,
                             UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.contentRepository = contentRepository;
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsForContent(Long contentId) {
        log.info("Fetching reviews for content ID: {}", contentId);
        List<Review> reviews = reviewRepository.findByContentId(contentId);
        return reviews.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReviewResponse addReview(Long contentId, Review review) {
        log.info("Adding review for content ID: {}", contentId);
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found with id: " + contentId));

        String currentEmail = SecurityUtils.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user context not found"));

        // Business Rule 1: Creator self-review prevention
        if (content.getCreator() != null && currentEmail.equalsIgnoreCase(content.getCreator().getEmail())) {
            throw new BadRequestException("Creators cannot review their own published content.");
        }

        // Business Rule 1: Verified Purchase Check (Enforce purchase requirement unless Admin)
        if (!SecurityUtils.isAdmin()) {
            boolean isPurchased = purchaseRepository.findByUserIdAndContentId(currentUser.getId(), contentId)
                    .map(p -> "SUCCESS".equalsIgnoreCase(p.getPaymentStatus()))
                    .orElse(false);

            if (!isPurchased) {
                throw new BadRequestException("You must purchase this resource before submitting a review.");
            }
        }

        // Business Rule 2: Duplicate Review Prevention Check
        boolean alreadyReviewed = reviewRepository.existsByContentIdAndUserId(contentId, currentUser.getId());
        if (alreadyReviewed) {
            throw new BadRequestException("You have already submitted a review for this resource.");
        }

        // Checkpoint 3.3: Rating Boundary Validation (1 to 5 stars)
        if (review.getRating() != null && (review.getRating() < 1 || review.getRating() > 5)) {
            throw new BadRequestException("Review rating must be between 1 and 5 stars.");
        }

        review.setContentId(contentId);
        review.setUserId(currentUser.getId());
        if (review.getReviewDate() == null) {
            review.setReviewDate(LocalDate.now().toString());
        }
        if (review.getStudentName() == null || review.getStudentName().isEmpty()) {
            review.setStudentName(currentUser.getName());
        }
        if (review.getAvatarUrl() == null) {
            review.setAvatarUrl(currentUser.getAvatarUrl() != null ? currentUser.getAvatarUrl() : "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150");
        }

        Review savedReview = reviewRepository.save(review);
        log.info("Successfully saved review ID: {} for content ID: {}", savedReview.getId(), contentId);

        // Update Content average rating and review count
        List<Review> allReviews = reviewRepository.findByContentId(contentId);
        int count = allReviews.size();
        double avgRating = allReviews.stream().mapToInt(r -> r.getRating() != null ? r.getRating() : 5).average().orElse(5.0);

        content.setReviewsCount(count);
        content.setRating(BigDecimal.valueOf(avgRating).setScale(1, RoundingMode.HALF_UP));
        contentRepository.save(content);

        return mapToResponse(savedReview);
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
