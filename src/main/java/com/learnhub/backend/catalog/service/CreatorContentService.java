package com.learnhub.backend.catalog.service;

import com.learnhub.backend.catalog.dto.CreateContentRequest;
import com.learnhub.backend.catalog.dto.ContentResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * CreatorContentService — Business logic interface for Content Authoring Studio.
 */
public interface CreatorContentService {

    /**
     * Publish Rich Text WYSIWYG Article or Generic Content Resource.
     */
    ContentResponse publishContent(CreateContentRequest request);

    /**
     * Upload PDF File Resource and save to server disk storage.
     */
    ContentResponse uploadPdfResource(
            MultipartFile file,
            String title,
            String description,
            Double price,
            String level,
            String tags,
            String status,
            Long categoryId,
            Long creatorId);
}
