package com.learnhub.backend.modules.resource.controller;

import com.learnhub.backend.common.dto.ApiResponse;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.common.util.SecurityUtils;
import com.learnhub.backend.modules.resource.dto.CreateContentRequest;
import com.learnhub.backend.modules.resource.dto.ContentResponse;
import com.learnhub.backend.modules.resource.dto.ContentStatusRequest;
import com.learnhub.backend.modules.resource.dto.UpdateContentRequest;
import com.learnhub.backend.modules.resource.service.CreatorContentService;
import com.learnhub.backend.modules.user.entity.User;
import com.learnhub.backend.modules.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * CreatorContentController — REST Controller for Content Authoring Studio & Creator Resource Management Grid.
 * Refactored with class-level @PreAuthorize and automatic JWT creator identity resolution.
 */
@RestController
@RequestMapping("/api/creator/content")
@PreAuthorize("hasRole('CREATOR') or hasRole('ADMIN')")
public class CreatorContentController {

    private final CreatorContentService creatorContentService;
    private final UserRepository userRepository;

    public CreatorContentController(CreatorContentService creatorContentService, UserRepository userRepository) {
        this.creatorContentService = creatorContentService;
        this.userRepository = userRepository;
    }

    /** GET /api/creator/content/status — Health check endpoint */
    @GetMapping("/status")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<String>> getStatus() {
        return ResponseEntity.ok(ApiResponse.success("Content Authoring Studio Module is Active", "OK"));
    }

    /** POST /api/creator/content/article — Publish Rich Text Article */
    @PostMapping("/article")
    public ResponseEntity<ApiResponse<ContentResponse>> publishArticle(@Valid @RequestBody CreateContentRequest request) {
        if (request.getCreatorId() == null) {
            request.setCreatorId(resolveCreatorId(null));
        }
        request.setType("ARTICLE");
        ContentResponse response = creatorContentService.publishContent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Article published successfully", response));
    }

    /** POST /api/creator/content/pdf — Upload PDF file resource with optional thumbnail image */
    @PostMapping("/pdf")
    public ResponseEntity<ApiResponse<ContentResponse>> uploadPdf(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price", defaultValue = "0.00") Double price,
            @RequestParam(value = "level", defaultValue = "Beginner") String level,
            @RequestParam(value = "tags", required = false) String tags,
            @RequestParam(value = "status", defaultValue = "PUBLISHED") String status,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "categoryName", required = false) String categoryName,
            @RequestParam(value = "creatorId", required = false) Long creatorId) {

        Long resolvedCreatorId = resolveCreatorId(creatorId);
        ContentResponse response = creatorContentService.uploadPdfResource(
                file, thumbnail, title, description, price, level, tags, status, categoryId, categoryName, resolvedCreatorId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("PDF resource uploaded and published successfully", response));
    }

    /** POST /api/creator/content — Generic resource creation */
    @PostMapping
    public ResponseEntity<ApiResponse<ContentResponse>> createContent(@Valid @RequestBody CreateContentRequest request) {
        if (request.getCreatorId() == null) {
            request.setCreatorId(resolveCreatorId(null));
        }
        ContentResponse response = creatorContentService.publishContent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Content resource created successfully", response));
    }

    /** GET /api/creator/content/my-resources — Get authenticated creator's resources */
    @GetMapping("/my-resources")
    public ResponseEntity<ApiResponse<List<ContentResponse>>> getMyResources() {
        Long resolvedCreatorId = resolveCreatorId(null);
        List<ContentResponse> resources = creatorContentService.getCreatorContents(resolvedCreatorId);
        return ResponseEntity.ok(ApiResponse.success("Creator resources retrieved successfully", resources));
    }

    /** GET /api/creator/content — Default get authenticated creator's resources */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ContentResponse>>> getAllResources() {
        return getMyResources();
    }

    /** GET /api/creator/content/{creatorId} — Get resources by specific numeric creator ID */
    @GetMapping("/{creatorId:\\d+}")
    public ResponseEntity<ApiResponse<List<ContentResponse>>> getResourcesByCreatorId(@PathVariable Long creatorId) {
        Long resolvedCreatorId = resolveCreatorId(creatorId);
        List<ContentResponse> resources = creatorContentService.getCreatorContents(resolvedCreatorId);
        return ResponseEntity.ok(ApiResponse.success("Creator resources retrieved successfully", resources));
    }

    /** PUT /api/creator/content/{id} — Edit resource */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ContentResponse>> updateContent(
            @PathVariable Long id,
            @Valid @RequestBody UpdateContentRequest request) {
        ContentResponse response = creatorContentService.updateContent(id, request);
        return ResponseEntity.ok(ApiResponse.success("Content resource updated successfully", response));
    }

    /** PATCH /api/creator/content/{id}/status — Toggle DRAFT / PUBLISHED status */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ContentResponse>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody ContentStatusRequest request) {
        ContentResponse response = creatorContentService.updateContentStatus(id, request.getStatus());
        return ResponseEntity.ok(ApiResponse.success("Resource status updated to " + request.getStatus(), response));
    }

    /** DELETE /api/creator/content/{id} — Delete resource */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteContent(@PathVariable Long id) {
        creatorContentService.deleteContent(id);
        return ResponseEntity.ok(ApiResponse.success("Content resource deleted successfully", "Resource " + id + " deleted"));
    }

    private Long resolveCreatorId(Long requestedCreatorId) {
        String currentEmail = SecurityUtils.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user context not found"));
        if (requestedCreatorId != null && !SecurityUtils.isAdmin()) {
            SecurityUtils.validateOwnershipById(currentUser.getId(), requestedCreatorId);
            return requestedCreatorId;
        }
        return (requestedCreatorId != null && SecurityUtils.isAdmin()) ? requestedCreatorId : currentUser.getId();
    }
}
