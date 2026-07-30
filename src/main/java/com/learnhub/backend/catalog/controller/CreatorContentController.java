package com.learnhub.backend.catalog.controller;

import com.learnhub.backend.catalog.dto.CreateContentRequest;
import com.learnhub.backend.catalog.dto.ContentResponse;
import com.learnhub.backend.catalog.dto.ContentStatusRequest;
import com.learnhub.backend.catalog.dto.UpdateContentRequest;
import com.learnhub.backend.catalog.service.CreatorContentService;
import com.learnhub.backend.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/*
 * CreatorContentController — REST Controller for Content Authoring Studio & Creator Resource Management Grid.
 */
@RestController
@RequestMapping("/api/creator/content")
public class CreatorContentController {

    private final CreatorContentService creatorContentService;

    // Explicit constructor for dependency injection
    public CreatorContentController(CreatorContentService creatorContentService) {
        this.creatorContentService = creatorContentService;
    }

    /*
     * GET /api/creator/content/status
     * Health check endpoint for Content Studio module.
     */
    @GetMapping("/status")
    public ResponseEntity<ApiResponse<String>> getStatus() {
        return ResponseEntity.ok(ApiResponse.success("Content Authoring Studio Module is Active", "OK"));
    }

    /*
     * POST /api/creator/content/article
     * Publish a new Rich Text Article created in the frontend WYSIWYG editor.
     */
    @PostMapping("/article")
    public ResponseEntity<ApiResponse<ContentResponse>> publishArticle(@Valid @RequestBody CreateContentRequest request) {
        request.setType("ARTICLE");
        ContentResponse response = creatorContentService.publishContent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Article published successfully", response));
    }

    /*
     * POST /api/creator/content/pdf
     * Upload a PDF file resource directly using multipart/form-data.
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
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "categoryName", required = false) String categoryName,
            @RequestParam("creatorId") Long creatorId) {

        ContentResponse response = creatorContentService.uploadPdfResource(
                file, title, description, price, level, tags, status, categoryId, categoryName, creatorId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("PDF resource uploaded and published successfully", response));
    }

    /*
     * POST /api/creator/content
     * Generic endpoint to create any learning resource via JSON.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ContentResponse>> createContent(@Valid @RequestBody CreateContentRequest request) {
        ContentResponse response = creatorContentService.publishContent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Content resource created successfully", response));
    }

    /*
     * GET /api/creator/content/my-resources/{creatorId}
     * Fetch all resources created by a creator for the Management Grid.
     */
    @GetMapping("/my-resources/{creatorId}")
    public ResponseEntity<ApiResponse<List<ContentResponse>>> getMyResources(@PathVariable Long creatorId) {
        List<ContentResponse> resources = creatorContentService.getCreatorContents(creatorId);
        return ResponseEntity.ok(ApiResponse.success("Creator resources retrieved successfully", resources));
    }

    /*
     * PUT /api/creator/content/{id}
     * Edit/Update an existing Article or PDF learning resource (title, description, contentBody, price, level, tags, status).
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ContentResponse>> updateContent(
            @PathVariable Long id,
            @Valid @RequestBody UpdateContentRequest request) {
        ContentResponse response = creatorContentService.updateContent(id, request);
        return ResponseEntity.ok(ApiResponse.success("Content resource updated successfully", response));
    }

    /*
     * PATCH /api/creator/content/{id}/status
     * Toggle resource status between DRAFT and PUBLISHED.
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ContentResponse>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody ContentStatusRequest request) {
        ContentResponse response = creatorContentService.updateContentStatus(id, request.getStatus());
        return ResponseEntity.ok(ApiResponse.success("Resource status updated to " + request.getStatus(), response));
    }

    /*
     * DELETE /api/creator/content/{id}
     * Delete a learning resource and update category resource counts.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteContent(@PathVariable Long id) {
        creatorContentService.deleteContent(id);
        return ResponseEntity.ok(ApiResponse.success("Content resource deleted successfully", "Resource " + id + " deleted"));
    }
}
