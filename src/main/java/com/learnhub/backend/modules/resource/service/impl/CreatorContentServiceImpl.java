package com.learnhub.backend.modules.resource.service.impl;

import com.learnhub.backend.modules.resource.dto.CreateContentRequest;
import com.learnhub.backend.modules.resource.dto.ContentResponse;
import com.learnhub.backend.modules.resource.dto.UpdateContentRequest;
import com.learnhub.backend.modules.resource.entity.Category;
import com.learnhub.backend.modules.resource.entity.Content;
import com.learnhub.backend.modules.resource.repository.CategoryRepository;
import com.learnhub.backend.modules.resource.repository.ContentRepository;
import com.learnhub.backend.modules.resource.service.CreatorContentService;
import com.learnhub.backend.common.exception.BadRequestException;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.common.util.SecurityUtils;
import com.learnhub.backend.modules.user.entity.User;
import com.learnhub.backend.modules.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * CreatorContentServiceImpl — Implementation class for Content Authoring Studio and Creator Resource Management Grid with SLF4J logging.
 */
@Service
public class CreatorContentServiceImpl implements CreatorContentService {

    private static final Logger log = LoggerFactory.getLogger(CreatorContentServiceImpl.class);

    private final ContentRepository contentRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private static final String UPLOAD_DIR = "uploads/pdfs/";

    public CreatorContentServiceImpl(ContentRepository contentRepository,
                                     CategoryRepository categoryRepository,
                                     UserRepository userRepository) {
        this.contentRepository = contentRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ContentResponse publishContent(CreateContentRequest request) {
        log.info("Publishing new content resource with title: '{}'", request.getTitle());

        String currentEmail = SecurityUtils.getCurrentUserEmail();
        User creatorUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated creator user context not found"));
        Long creatorId = creatorUser.getId();

        Category category = resolveCategory(request.getCategoryId(), request.getCategoryName());

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
        content.setApprovalStatus("APPROVED");
        content.setCategory(category);
        if (category != null) content.setCategoryId(category.getId());
        content.setCreator(creatorUser);
        if (creatorUser != null) content.setCreatorId(creatorUser.getId());

        Content savedContent = contentRepository.save(content);
        log.info("Successfully published content ID: {} by creator ID: {}", savedContent.getId(), creatorId);

        if (category != null) {
            category.setResourceCount((category.getResourceCount() == null ? 0 : category.getResourceCount()) + 1);
            categoryRepository.save(category);
        }

        return mapToResponse(savedContent);
    }

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

        log.info("Uploading PDF resource with title: '{}'", title);
        if (file == null || file.isEmpty()) {
            log.warn("Upload failed. Provided PDF file is empty.");
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

    @Override
    public List<ContentResponse> getCreatorContents(Long creatorId) {
        log.info("Fetching all created resources for creator ID: {}", creatorId);
        userRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Creator not found with id: " + creatorId));

        return contentRepository.findByCreatorId(creatorId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ContentResponse updateContent(Long contentId, UpdateContentRequest request) {
        log.info("Updating content ID: {}", contentId);
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found with id: " + contentId));

        if (content.getCreator() != null) {
            SecurityUtils.validateOwnership(content.getCreator().getEmail());
        }

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
        log.info("Successfully updated content ID: {}", updatedContent.getId());
        return mapToResponse(updatedContent);
    }

    @Override
    @Transactional
    public ContentResponse updateContentStatus(Long contentId, String status) {
        log.info("Updating status of content ID: {} to {}", contentId, status);
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found with id: " + contentId));

        if (content.getCreator() != null) {
            SecurityUtils.validateOwnership(content.getCreator().getEmail());
        }

        content.setStatus(status);
        Content updatedContent = contentRepository.save(content);
        log.info("Successfully updated status of content ID: {} to {}", updatedContent.getId(), status);
        return mapToResponse(updatedContent);
    }

    @Override
    @Transactional
    public void deleteContent(Long contentId) {
        log.info("Deleting content ID: {}", contentId);
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found with id: " + contentId));

        if (content.getCreator() != null) {
            SecurityUtils.validateOwnership(content.getCreator().getEmail());
        }

        if (content.getCategoryId() != null) {
            categoryRepository.findById(content.getCategoryId()).ifPresent(category -> {
                int currentCount = category.getResourceCount() == null ? 0 : category.getResourceCount();
                category.setResourceCount(Math.max(0, currentCount - 1));
                categoryRepository.save(category);
            });
        }

        contentRepository.delete(content);
        log.info("Successfully deleted content ID: {}", contentId);
    }

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
            log.error("Failed to store uploaded file locally: {}", e.getMessage(), e);
            throw new BadRequestException("Could not store file: " + e.getMessage());
        }
    }

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
