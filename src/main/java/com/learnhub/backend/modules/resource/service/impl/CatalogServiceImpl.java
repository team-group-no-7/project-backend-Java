package com.learnhub.backend.modules.resource.service.impl;

import com.learnhub.backend.modules.payment.repository.PurchaseRepository;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.modules.resource.dto.ContentRequest;
import com.learnhub.backend.modules.resource.dto.ContentResponse;
import com.learnhub.backend.modules.resource.entity.Category;
import com.learnhub.backend.modules.resource.entity.Content;
import com.learnhub.backend.modules.resource.repository.CategoryRepository;
import com.learnhub.backend.modules.resource.repository.ContentRepository;
import com.learnhub.backend.modules.resource.service.CatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CatalogServiceImpl — Implementation class for Resource Catalog browsing and operations.
 * Refactored with explicit constructor injection, custom exceptions, and SLF4J logging.
 */
@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {

    private static final Logger log = LoggerFactory.getLogger(CatalogServiceImpl.class);

    private final ContentRepository contentRepository;
    private final CategoryRepository categoryRepository;
    private final PurchaseRepository purchaseRepository;

    public CatalogServiceImpl(ContentRepository contentRepository,
                              CategoryRepository categoryRepository,
                              PurchaseRepository purchaseRepository) {
        this.contentRepository = contentRepository;
        this.categoryRepository = categoryRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentResponse> getCatalog(String search, String category) {
        log.info("Fetching catalog with search: '{}', category: '{}'", search, category);
        List<Content> contents = contentRepository.searchAndFilter(search, category);
        return contents.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ContentResponse getContentById(Long id) {
        log.info("Fetching content details for content ID: {}", id);
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found with id: " + id));
        return mapToResponse(content);
    }

    @Override
    public ContentResponse uploadContent(ContentRequest request) {
        log.info("Uploading content title: '{}'", request.getTitle());
        Content content = new Content();
        content.setTitle(request.getTitle());
        content.setDescription(request.getDescription());
        content.setPreviewText(request.getPreviewText());
        content.setContentBody(request.getContentBody());
        content.setFileUrl(request.getFileUrl());
        content.setPrice(request.getPrice());
        content.setType(request.getType());
        content.setLevel(request.getLevel());
        content.setTags(request.getTags());
        content.setCreatorId(request.getCreatorId());

        Category category = categoryRepository.findByName(request.getCategoryName())
                .orElseGet(() -> {
                    Category newCat = new Category();
                    newCat.setName(request.getCategoryName());
                    newCat.setResourceCount(0);
                    return categoryRepository.save(newCat);
                });

        content.setCategory(category);
        category.setResourceCount((category.getResourceCount() != null ? category.getResourceCount() : 0) + 1);
        categoryRepository.save(category);

        Content savedContent = contentRepository.save(content);
        log.info("Successfully uploaded content ID: {}", savedContent.getId());
        return mapToResponse(savedContent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentResponse> getContentsByCreator(Long creatorId) {
        log.info("Fetching contents for creator ID: {}", creatorId);
        List<Content> contents = contentRepository.findByCreatorId(creatorId);
        return contents.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteContent(Long id) {
        log.info("Deleting content ID: {}", id);
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found with id: " + id));

        if (content.getCreator() != null) {
            com.learnhub.backend.common.util.SecurityUtils.validateOwnership(content.getCreator().getEmail());
        }

        Category category = content.getCategory();
        if (category != null && category.getResourceCount() != null && category.getResourceCount() > 0) {
            category.setResourceCount(category.getResourceCount() - 1);
            categoryRepository.save(category);
        }

        contentRepository.delete(content);
        log.info("Successfully deleted content ID: {}", id);
    }

    private ContentResponse mapToResponse(Content content) {
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
        // Dynamically compute enrolled learners count from purchase transaction history in DB
        long dbPurchasesCount = purchaseRepository.countByContentId(content.getId());
        int totalLearners = (int) Math.max(dbPurchasesCount, content.getLearnersCount() != null ? content.getLearnersCount() : 0);
        response.setLearnersCount(totalLearners);
        response.setApprovalStatus(content.getApprovalStatus());
        response.setCreatorId(content.getCreatorId());
        response.setCreatedAt(content.getCreatedAt());

        if (content.getCategory() != null) {
            response.setCategoryName(content.getCategory().getName());
        }

        if (content.getCreator() != null) {
            response.setCreatorName(content.getCreator().getName());
            response.setCreatorAvatar(content.getCreator().getAvatarUrl());
        } else {
            response.setCreatorName("Unknown");
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentResponse> getFeaturedContents() {
        log.info("Fetching featured contents");
        List<Content> contents = contentRepository.findAll();
        List<Content> featured = contents.stream().filter(Content::isFeatured).collect(Collectors.toList());
        if (featured.isEmpty()) {
            featured = contents.stream().limit(3).collect(Collectors.toList());
        }
        return featured.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        log.info("Fetching all categories");
        return categoryRepository.findAll();
    }
}
