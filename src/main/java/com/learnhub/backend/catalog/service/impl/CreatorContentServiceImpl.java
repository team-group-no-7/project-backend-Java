package com.learnhub.backend.catalog.service.impl;

import com.learnhub.backend.catalog.dto.CreateContentRequest;
import com.learnhub.backend.catalog.dto.ContentResponse;
import com.learnhub.backend.catalog.dto.UpdateContentRequest;
import com.learnhub.backend.catalog.entity.Category;
import com.learnhub.backend.catalog.entity.Content;
import com.learnhub.backend.catalog.repository.CategoryRepository;
import com.learnhub.backend.catalog.repository.ContentRepository;
import com.learnhub.backend.catalog.service.CreatorContentService;
import com.learnhub.backend.common.exception.BadRequestException;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/*
 * CreatorContentServiceImpl — Implementation class for Content Authoring Studio and Creator Resource Management Grid.
 */
@Service
public class CreatorContentServiceImpl implements CreatorContentService {

    private final ContentRepository contentRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    // Directory for storing uploaded PDF files locally
    private static final String UPLOAD_DIR = "uploads/pdfs/";

    // Explicit constructor for dependency injection
    public CreatorContentServiceImpl(ContentRepository contentRepository,
                                     CategoryRepository categoryRepository,
                                     UserRepository userRepository) {
        this.contentRepository = contentRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    /*
     * Publish Rich Text WYSIWYG Article or Generic Content Resource.
     */
    @Override
    @Transactional
    public ContentResponse publishContent(CreateContentRequest request) {

        // Step 1: Verify Creator exists
        userRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new ResourceNotFoundException("Creator not found with id: " + request.getCreatorId()));

        // Step 2: Smart Category Resolution (by Name or by ID, with Auto-Creation)
        Category category = resolveCategory(request.getCategoryId(), request.getCategoryName());

        // Step 3: Build Content Entity
        Content content = new Content();
        content.setTitle(request.getTitle());
        content.setDescription(request.getDescription());
        content.setPreviewText(request.getPreviewText());
        content.setContentBody(request.getContentBody());
        content.setFileUrl(request.getFileUrl());
        content.setPrice(BigDecimal.valueOf(request.getPrice() != null ? request.getPrice() : 0.00));
        content.setType(request.getType() != null ? request.getType() : "ARTICLE");
        content.setLevel(request.getLevel() != null ? request.getLevel() : "Beginner");
        content.setTags(request.getTags());
        content.setStatus(request.getStatus() != null ? request.getStatus() : "PUBLISHED");
        content.setCategoryId(category.getId());
        content.setCreatorId(request.getCreatorId());

        // Step 4: Save Content to database
        Content savedContent = contentRepository.save(content);

        // Step 5: Increment Category resource count
        category.setResourceCount((category.getResourceCount() == null ? 0 : category.getResourceCount()) + 1);
        categoryRepository.save(category);

        return mapToResponse(savedContent);
    }

    /*
     * Upload PDF File Resource and save to server disk storage.
     */
    @Override
    @Transactional
    public ContentResponse uploadPdfResource(
            MultipartFile file,
            String title,
            String description,
            Double price,
            String level,
            String tags,
            String status,
            Long categoryId,
            String categoryName,
            Long creatorId) {

        if (file == null || file.isEmpty()) {
            throw new BadRequestException("PDF file cannot be empty");
        }

        String fileUrl = storeFileLocally(file);

        CreateContentRequest request = new CreateContentRequest();
        request.setTitle(title);
        request.setDescription(description);
        request.setFileUrl(fileUrl);
        request.setPrice(price);
        request.setType("PDF");
        request.setLevel(level);
        request.setTags(tags);
        request.setStatus(status != null ? status : "PUBLISHED");
        request.setCategoryId(categoryId);
        request.setCategoryName(categoryName);
        request.setCreatorId(creatorId);

        return publishContent(request);
    }

    /*
     * Fetch all learning resources created by a specific creator (for Management Grid).
     */
    @Override
    public List<ContentResponse> getCreatorContents(Long creatorId) {
        userRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Creator not found with id: " + creatorId));

        return contentRepository.findByCreatorId(creatorId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /*
     * Update/Edit existing content resource details (Title, Description, Rich Text HTML, Price, Level, Tags, Status, Category).
     */
    @Override
    @Transactional
    public ContentResponse updateContent(Long contentId, UpdateContentRequest request) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found with id: " + contentId));

        if (request.getTitle() != null && !request.getTitle().trim().isEmpty()) {
            content.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            content.setDescription(request.getDescription());
        }
        if (request.getPreviewText() != null) {
            content.setPreviewText(request.getPreviewText());
        }
        if (request.getContentBody() != null) {
            content.setContentBody(request.getContentBody());
        }
        if (request.getFileUrl() != null) {
            content.setFileUrl(request.getFileUrl());
        }
        if (request.getPrice() != null) {
            content.setPrice(BigDecimal.valueOf(request.getPrice()));
        }
        if (request.getType() != null) {
            content.setType(request.getType());
        }
        if (request.getLevel() != null) {
            content.setLevel(request.getLevel());
        }
        if (request.getTags() != null) {
            content.setTags(request.getTags());
        }
        if (request.getStatus() != null) {
            content.setStatus(request.getStatus());
        }
        if (request.getCategoryName() != null || request.getCategoryId() != null) {
            Category category = resolveCategory(request.getCategoryId(), request.getCategoryName());
            content.setCategoryId(category.getId());
        }

        Content updatedContent = contentRepository.save(content);
        return mapToResponse(updatedContent);
    }

    /*
     * Toggle content status between DRAFT and PUBLISHED.
     */
    @Override
    @Transactional
    public ContentResponse updateContentStatus(Long contentId, String status) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found with id: " + contentId));

        content.setStatus(status);
        Content updatedContent = contentRepository.save(content);
        return mapToResponse(updatedContent);
    }

    /*
     * Delete a learning resource and update category resource count.
     */
    @Override
    @Transactional
    public void deleteContent(Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found with id: " + contentId));

        if (content.getCategoryId() != null) {
            categoryRepository.findById(content.getCategoryId()).ifPresent(category -> {
                int currentCount = category.getResourceCount() == null ? 0 : category.getResourceCount();
                category.setResourceCount(Math.max(0, currentCount - 1));
                categoryRepository.save(category);
            });
        }

        contentRepository.delete(content);
    }

    /*
     * Helper method to resolve Category by Name or ID with automatic creation if missing.
     */
    private Category resolveCategory(Long categoryId, String categoryName) {
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            String trimmedName = categoryName.trim();
            return categoryRepository.findByName(trimmedName)
                    .orElseGet(() -> categoryRepository.save(new Category(trimmedName)));
        } else if (categoryId != null) {
            return categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        } else {
            return categoryRepository.findByName("General")
                    .orElseGet(() -> categoryRepository.save(new Category("General")));
        }
    }

    /*
     * Helper method to save uploaded file locally and return file access URL.
     */
    private String storeFileLocally(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/" + UPLOAD_DIR + uniqueFilename;
        } catch (IOException e) {
            throw new BadRequestException("Could not store file: " + e.getMessage());
        }
    }

    /*
     * Helper method to map Content entity to ContentResponse DTO.
     */
    private ContentResponse mapToResponse(Content content) {
        ContentResponse response = new ContentResponse(
                content.getId(),
                content.getTitle(),
                content.getDescription(),
                content.getPreviewText(),
                content.getContentBody(),
                content.getFileUrl(),
                content.getPrice(),
                content.getType(),
                content.getLevel(),
                content.getTags(),
                content.getStatus(),
                content.getFeatured(),
                content.getIsTrending(),
                content.getRating(),
                content.getReviewsCount(),
                content.getLearnersCount(),
                content.getCategoryId(),
                content.getCreatorId(),
                content.getCreatedAt()
        );
        if (content.getCreator() != null) {
            response.setCreatorName(content.getCreator().getName());
            response.setCreatorAvatar(content.getCreator().getAvatarUrl());
        } else {
            response.setCreatorName("Unknown");
        }
        if (content.getCategory() != null) {
            response.setCategoryName(content.getCategory().getName());
        }
        return response;
    }
}
