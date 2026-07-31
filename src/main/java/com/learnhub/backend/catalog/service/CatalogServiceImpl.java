package com.learnhub.backend.catalog.service;

import com.learnhub.backend.catalog.dto.ContentRequest;
import com.learnhub.backend.catalog.dto.ContentResponse;
import com.learnhub.backend.catalog.entity.Category;
import com.learnhub.backend.catalog.entity.Content;
import com.learnhub.backend.catalog.repository.CategoryRepository;
import com.learnhub.backend.catalog.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ContentResponse> getCatalog(String search, String category) {
        List<Content> contents = contentRepository.searchAndFilter(search, category);
        return contents.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ContentResponse getContentById(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found with id: " + id));
        return mapToResponse(content);
    }

    @Override
    public ContentResponse uploadContent(ContentRequest request) {
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

        // Find or create Category
        Category category = categoryRepository.findByName(request.getCategoryName())
                .orElseGet(() -> {
                    Category newCat = new Category();
                    newCat.setName(request.getCategoryName());
                    newCat.setResourceCount(0);
                    return categoryRepository.save(newCat);
                });

        content.setCategory(category);
        
        // Increment the category's resource counter
        category.setResourceCount(category.getResourceCount() + 1);
        categoryRepository.save(category);

        Content savedContent = contentRepository.save(content);
        return mapToResponse(savedContent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentResponse> getContentsByCreator(Long creatorId) {
        List<Content> contents = contentRepository.findByCreatorId(creatorId);
        return contents.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteContent(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found with id: " + id));
        
        Category category = content.getCategory();
        if (category != null && category.getResourceCount() > 0) {
            category.setResourceCount(category.getResourceCount() - 1);
            categoryRepository.save(category);
        }
        
        contentRepository.delete(content);
    }

    // Helper mapper method
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
        response.setLearnersCount(content.getLearnersCount());
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
        return categoryRepository.findAll();
    }
}
