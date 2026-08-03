package com.learnhub.backend.modules.resource.service.impl;

import com.learnhub.backend.common.dto.PlatformStatsResponse;
import com.learnhub.backend.modules.resource.dto.ContentResponse;
import com.learnhub.backend.modules.resource.dto.response.ContentReaderResponse;
import com.learnhub.backend.modules.resource.dto.response.CatalogResponse;
import com.learnhub.backend.modules.resource.entity.Content;
import com.learnhub.backend.modules.resource.enums.ApprovalStatus;
import com.learnhub.backend.modules.resource.repository.ContentRepository;
import com.learnhub.backend.modules.resource.service.ContentService;
import com.learnhub.backend.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ContentServiceImpl — Implementation class for Catalog Browsing & Moderation Service.
 * Implemented in pure Java with explicit constructor injection (no Lombok).
 */
@Service
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;
    private final UserRepository userRepository;

    // Explicit Constructor for Dependency Injection
    public ContentServiceImpl(ContentRepository contentRepository, UserRepository userRepository) {
        this.contentRepository = contentRepository;
        this.userRepository = userRepository;
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
                content.getFileUrl(), // Using fileUrl as thumbnail fallback
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
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Content not found with id: " + contentId));

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
        return contentRepository.findAll()
                .stream()
                .map(this::mapToCatalog)
                .collect(Collectors.toList());
    }

    @Override
    public List<CatalogResponse> search(String keyword) {
        return contentRepository.findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToCatalog)
                .collect(Collectors.toList());
    }

    @Override
    public List<CatalogResponse> getByCategory(Long categoryId) {
        return contentRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::mapToCatalog)
                .collect(Collectors.toList());
    }

    // Admin & Moderation Implementations with Typed ApprovalStatus Enum
    @Override
    @Transactional(readOnly = true)
    public PlatformStatsResponse getPlatformStats() {
        PlatformStatsResponse stats = new PlatformStatsResponse();
        stats.setTotalUsers(userRepository.count());
        stats.setTotalContents(contentRepository.count());
        stats.setTotalRevenue(BigDecimal.valueOf(14200.00));
        return stats;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentResponse> getAllContentResponses() {
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
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Content not found with id: " + contentId));

        content.setApprovalStatus(status.name());
        Content savedContent = contentRepository.save(content);
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