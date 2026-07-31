package com.learnhub.backend.catalog.service;

import com.learnhub.backend.catalog.dto.CreateContentRequest;
import com.learnhub.backend.catalog.dto.ContentResponse;
import com.learnhub.backend.catalog.dto.UpdateContentRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/*
 * CreatorContentService — Business logic interface for Content Authoring Studio and Creator Resource Management Grid.
 */
public interface CreatorContentService {

    /*
     * Publish Rich Text WYSIWYG Article or Generic Content Resource.
     */
    ContentResponse publishContent(CreateContentRequest request);

    /*
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
            String categoryName,
            Long creatorId);

    /*
     * Fetch all learning resources created by a specific creator.
     */
    List<ContentResponse> getCreatorContents(Long creatorId);

    /*
     * Update/Edit existing content resource details.
     */
    ContentResponse updateContent(Long contentId, UpdateContentRequest request);

    /*
     * Toggle content status between DRAFT and PUBLISHED.
     */
    ContentResponse updateContentStatus(Long contentId, String status);

    /*
     * Delete a learning resource and update category resource count.
     */
    void deleteContent(Long contentId);
}
