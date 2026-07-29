package com.learnhub.backend.catalog.controller;

import com.learnhub.backend.catalog.dto.CreateContentRequest;
import com.learnhub.backend.catalog.dto.ContentResponse;
import com.learnhub.backend.catalog.service.CreatorContentService;
import com.learnhub.backend.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * CreatorContentController — REST Controller for Content Authoring Studio.
 *
 * Handles publishing Rich Text WYSIWYG Articles and Multipart PDF Resource Uploads.
 * Base Path: /api/creator/content (matching Handbook Matrix 15A)
 * Implemented in pure Java with explicit constructor dependency injection (no Lombok).
 */
@RestController
@RequestMapping("/api/creator/content")
public class CreatorContentController {

    private final CreatorContentService creatorContentService;

    // Explicit constructor for dependency injection
    public CreatorContentController(CreatorContentService creatorContentService) {
        this.creatorContentService = creatorContentService;
    }

    /**
     * GET /api/creator/content/status
     * Health check endpoint for Content Studio module.
     */
    @GetMapping("/status")
    public ResponseEntity<ApiResponse<String>> getStatus() {
        return ResponseEntity.ok(ApiResponse.success("Content Authoring Studio Module is Active", "OK"));
    }

    /**
     * POST /api/creator/content/article
     * Publish a new Rich Text Article created in the frontend WYSIWYG editor.
     *
     * @param request JSON body carrying contentBody (HTML/Markdown), title, categoryId, creatorId
     * @return ContentResponse wrapped in ApiResponse
     */
    @PostMapping("/article")
    public ResponseEntity<ApiResponse<ContentResponse>> publishArticle(@Valid @RequestBody CreateContentRequest request) {
        request.setType("ARTICLE");
        ContentResponse response = creatorContentService.publishContent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Article published successfully", response));
    }

    /**
     * POST /api/creator/content/pdf
     * Upload a PDF file resource directly using multipart/form-data.
     *
     * @param file uploaded PDF file
     * @param title resource title
     * @param description resource description
     * @param price resource price
     * @param level difficulty level
     * @param tags comma-separated search tags
     * @param status DRAFT or PUBLISHED
     * @param categoryId category ID
     * @param creatorId creator ID
     * @return ContentResponse wrapped in ApiResponse
     */
    @PostMapping("/pdf")
    public ResponseEntity<ApiResponse<ContentResponse>> uploadPdf(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price", defaultValue = "0.00") Double price,
            @RequestParam(value = "level", defaultValue = "Beginner") String level,
            @RequestParam(value = "tags", required = false) String tags,
            @RequestParam(value = "status", defaultValue = "PUBLISHED") String status,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("creatorId") Long creatorId) {

        ContentResponse response = creatorContentService.uploadPdfResource(
                file, title, description, price, level, tags, status, categoryId, creatorId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("PDF resource uploaded and published successfully", response));
    }

    /**
     * POST /api/creator/content
     * Generic endpoint to create any learning resource via JSON.
     *
     * @param request JSON body with resource details
     * @return ContentResponse wrapped in ApiResponse
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ContentResponse>> createContent(@Valid @RequestBody CreateContentRequest request) {
        ContentResponse response = creatorContentService.publishContent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Content resource created successfully", response));
    }
}
