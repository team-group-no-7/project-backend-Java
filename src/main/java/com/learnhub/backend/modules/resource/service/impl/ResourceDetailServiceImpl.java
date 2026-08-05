package com.learnhub.backend.modules.resource.service.impl;

import com.learnhub.backend.modules.payment.repository.PurchaseRepository;
import com.learnhub.backend.modules.resource.dto.ResourceDetailResponse;
import com.learnhub.backend.modules.resource.dto.ReviewDto;
import com.learnhub.backend.modules.resource.entity.Content;
import com.learnhub.backend.modules.resource.entity.Review;
import com.learnhub.backend.modules.resource.repository.ContentRepository;
import com.learnhub.backend.modules.resource.repository.ReviewRepository;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.modules.resource.service.ResourceDetailService;
import com.learnhub.backend.modules.user.entity.User;
import com.learnhub.backend.modules.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ResourceDetailServiceImpl — Implementation of ResourceDetailService with SLF4J logging.
 */
@Service
@Transactional(readOnly = true)
public class ResourceDetailServiceImpl implements ResourceDetailService {

    private static final Logger log = LoggerFactory.getLogger(ResourceDetailServiceImpl.class);

    private final ContentRepository contentRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;

    public ResourceDetailServiceImpl(ContentRepository contentRepository,
                                     ReviewRepository reviewRepository,
                                     UserRepository userRepository,
                                     PurchaseRepository purchaseRepository) {
        this.contentRepository = contentRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public ResourceDetailResponse getResourceDetails(Long id) {
        log.info("Fetching public resource page details for content ID: {}", id);
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));

        ResourceDetailResponse response = new ResourceDetailResponse();
        response.setId(content.getId());
        response.setTitle(content.getTitle());
        response.setDescription(content.getDescription());
        response.setPreviewText(content.getPreviewText());
        response.setContentBody(content.getContentBody());
        response.setFileUrl(content.getFileUrl());
        response.setThumbnailUrl(content.getThumbnailUrl());
        response.setPrice(content.getPrice());
        response.setType(content.getType() != null ? content.getType() : "Notes & Code");
        response.setLevel(content.getLevel() != null ? content.getLevel() : "Intermediate");

        if (content.getTags() != null && !content.getTags().trim().isEmpty()) {
            List<String> tagList = Arrays.stream(content.getTags().split(","))
                    .map(String::trim)
                    .filter(t -> !t.isEmpty())
                    .collect(Collectors.toList());
            response.setTags(tagList);
        } else {
            response.setTags(Arrays.asList("Java", "Spring Boot", "Backend"));
        }

        response.setRating(content.getRating() != null ? content.getRating() : java.math.BigDecimal.valueOf(4.8));
        response.setReviewsCount(content.getReviewsCount() != null ? content.getReviewsCount() : 0);

        // Dynamically compute enrolled learners count from purchase transaction history in DB
        long dbPurchasesCount = purchaseRepository.countByContentId(id);
        int totalLearners = (int) Math.max(dbPurchasesCount, content.getLearnersCount() != null ? content.getLearnersCount() : 0);
        response.setLearnersCount(totalLearners);

        response.setCreatedAt(content.getCreatedAt() != null ? content.getCreatedAt().toString() : "2026-06-10");

        if (content.getCategory() != null) {
            response.setCategoryId(content.getCategory().getId());
            response.setCategoryName(content.getCategory().getName());
        } else {
            response.setCategoryName("General");
        }

        if (content.getCreatorId() != null) {
            response.setCreatorId(content.getCreatorId());
            User creator = userRepository.findById(content.getCreatorId()).orElse(null);
            if (creator != null) {
                response.setCreatorName(creator.getName());
                response.setCreatorAvatar(creator.getAvatarUrl());
            } else {
                response.setCreatorName("LearnHub Expert");
                response.setCreatorAvatar("https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150");
            }
        } else {
            response.setCreatorName("LearnHub Expert");
            response.setCreatorAvatar("https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150");
        }

        List<Review> dbReviews = reviewRepository.findByContentId(id);
        List<ReviewDto> reviewDtos = dbReviews.stream()
                .map(r -> new ReviewDto(r.getId(), r.getStudentName(), r.getAvatarUrl(),
                        r.getRating(), r.getReviewText(), r.getReviewDate()))
                .collect(Collectors.toList());

        response.setReviews(reviewDtos);
        return response;
    }
}
