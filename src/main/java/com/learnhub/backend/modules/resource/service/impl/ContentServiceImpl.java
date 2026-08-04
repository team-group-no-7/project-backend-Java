package com.learnhub.backend.modules.resource.service.impl;

import com.learnhub.backend.common.dto.PlatformStatsResponse;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.common.util.SecurityUtils;
import com.learnhub.backend.modules.payment.repository.PurchaseRepository;
import com.learnhub.backend.modules.resource.dto.ContentResponse;
import com.learnhub.backend.modules.resource.dto.response.ContentReaderResponse;
import com.learnhub.backend.modules.resource.dto.response.CatalogResponse;
import com.learnhub.backend.modules.resource.entity.Content;
import com.learnhub.backend.modules.resource.enums.ApprovalStatus;
import com.learnhub.backend.modules.resource.repository.ContentRepository;
import com.learnhub.backend.modules.resource.service.ContentService;
import com.learnhub.backend.modules.user.entity.User;
import com.learnhub.backend.modules.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ContentServiceImpl — Implementation class for Catalog Browsing & Moderation Service with SLF4J logging.
 */
@Service
public class ContentServiceImpl implements ContentService {

    private static final Logger log = LoggerFactory.getLogger(ContentServiceImpl.class);

    private final ContentRepository contentRepository;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;

    public ContentServiceImpl(ContentRepository contentRepository,
                              UserRepository userRepository,
                              PurchaseRepository purchaseRepository) {
        this.contentRepository = contentRepository;
        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
    }

    private CatalogResponse mapToCatalog(Content content) {
        String categoryName = content.getCategory() != null ? content.getCategory().getName() : "General";
        String creatorName = content.getCreator() != null ? content.getCreator().getName() : "LearnHub Creator";
        String creatorAvatar = content.getCreator() != null ? content.getCreator().getAvatarUrl() : null;
        Long creatorId = content.getCreator() != null ? content.getCreator().getId() : null;
        String typeName = content.getType() != null ? content.getType() : "ARTICLE";

        return new CatalogResponse(
                content.getId(),
                content.getTitle(),
                content.getDescription(),
                typeName,
                categoryName,
                content.getPrice(),
                content.getFileUrl(),
                creatorName,
                creatorAvatar,
                creatorId,
                content.isTrending(),
                content.isFeatured(),
                content.getRating() != null ? content.getRating().doubleValue() : null,
                content.getReviewsCount(),
                content.getLearnersCount()
        );
    }

    @Override
    public ContentReaderResponse getContent(Long contentId) {
        log.info("Fetching content reader view for ID: {}", contentId);
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found with id: " + contentId));

        // Business Rule 5: Entitlement Check for Paid Reader Resources
        if (content.getPrice() != null && content.getPrice().compareTo(BigDecimal.ZERO) > 0) {
            if (!SecurityUtils.isAdmin()) {
                String currentEmail = SecurityUtils.getCurrentUserEmail();
                boolean isAuthor = content.getCreator() != null && currentEmail.equalsIgnoreCase(content.getCreator().getEmail());
                if (!isAuthor) {
                    User currentUser = userRepository.findByEmail(currentEmail)
                            .orElseThrow(() -> new ResourceNotFoundException("Authenticated user context not found"));

                    boolean isPurchased = purchaseRepository.findByUserIdAndContentId(currentUser.getId(), contentId)
                            .map(p -> "SUCCESS".equalsIgnoreCase(p.getPaymentStatus()))
                            .orElse(false);

                    if (!isPurchased) {
                        log.warn("Entitlement access denied for user: {} on paid content ID: {}", currentEmail, contentId);
                        throw new AccessDeniedException("You must purchase this paid resource to access the full content reader.");
                    }
                }
            }
        }

        String categoryName = content.getCategory() != null ? content.getCategory().getName() : "General";
        String typeName = content.getType() != null ? content.getType() : "ARTICLE";

        return new ContentReaderResponse(
                content.getId(),
                content.getTitle(),
                content.getDescription(),
                typeName,
                content.getContentBody(),
                content.getFileUrl(),
                content.getPrice(),
                categoryName
        );
    }

    @Override
    public List<CatalogResponse> getAllContents() {
        log.info("Fetching all catalog contents");
        return contentRepository.findAll()
                .stream()
                .map(this::mapToCatalog)
                .collect(Collectors.toList());
    }

    @Override
    public List<CatalogResponse> search(String keyword) {
        log.info("Searching catalog with keyword: '{}'", keyword);
        return contentRepository.findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToCatalog)
                .collect(Collectors.toList());
    }

    @Override
    public List<CatalogResponse> getByCategory(Long categoryId) {
        log.info("Fetching catalog for category ID: {}", categoryId);
        return contentRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::mapToCatalog)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PlatformStatsResponse getPlatformStats() {
        log.info("Computing platform analytics statistics");
        PlatformStatsResponse stats = new PlatformStatsResponse();
        stats.setTotalUsers(userRepository.count());
        stats.setTotalContents(contentRepository.count());
        stats.setTotalRevenue(BigDecimal.valueOf(14200.00));
        return stats;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentResponse> getAllContentResponses() {
        log.info("Fetching all content responses for admin moderation");
        return contentRepository.findAll()
                .stream()
                .map(this::mapToContentResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ContentResponse approveContent(Long contentId) {
        return updateApprovalStatus(contentId, ApprovalStatus.APPROVED);
    }

    @Override
    @Transactional
    public ContentResponse flagContent(Long contentId) {
        return updateApprovalStatus(contentId, ApprovalStatus.FLAGGED);
    }

    @Override
    @Transactional
    public ContentResponse updateApprovalStatus(Long contentId, ApprovalStatus status) {
        log.info("Updating content ID: {} approval status to {}", contentId, status);
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found with id: " + contentId));

        content.setApprovalStatus(status.name());
        Content savedContent = contentRepository.save(content);
        log.info("Successfully updated content ID: {} approval status to {}", savedContent.getId(), status);
        return mapToContentResponse(savedContent);
    }

    private ContentResponse mapToContentResponse(Content content) {
        ContentResponse response = new ContentResponse();
        response.setId(content.getId());
        response.setTitle(content.getTitle());
        response.setDescription(content.getDescription());
        response.setPreviewText(content.getPreviewText());
        response.setContentBody(content.getContentBody());
        response.setFileUrl(content.getFileUrl());
        response.setPrice(content.getPrice());
        response.setType(content.getType());
        response.setLevel(content.getLevel());
        response.setTags(content.getTags());
        response.setFeatured(content.isFeatured());
        response.setTrending(content.isTrending());
        response.setRating(content.getRating());
        response.setReviewsCount(content.getReviewsCount());
        response.setLearnersCount(content.getLearnersCount());
        response.setApprovalStatus(content.getApprovalStatus());
        response.setCreatorId(content.getCreatorId());
        response.setCreatedAt(content.getCreatedAt());

        if (content.getCategory() != null) {
            response.setCategoryName(content.getCategory().getName());
        }
        return response;
    }
}