package com.learnhub.backend.modules.resource.controller;

import com.learnhub.backend.common.dto.ApiResponse;
import com.learnhub.backend.modules.resource.dto.response.ContentReaderResponse;
import com.learnhub.backend.modules.resource.dto.response.CatalogResponse;
import com.learnhub.backend.modules.resource.service.ContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ContentController — REST Controller for Learner Catalog Browsing and Content Reader APIs.
 * Supports both query parameter filtering (?categoryId=1) and explicit path variables (/category/1).
 */
@RestController
@RequestMapping("/api/contents")
public class ContentController {

    private final ContentService contentService;

    // Explicit Constructor Injection
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContentReaderResponse>> getContent(@PathVariable Long id) {
        ContentReaderResponse content = contentService.getContent(id);
        return ResponseEntity.ok(ApiResponse.success("Content details retrieved successfully", content));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CatalogResponse>>> allContents(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "search", required = false) String search) {
        
        List<CatalogResponse> contents;
        if (search != null && !search.trim().isEmpty()) {
            contents = contentService.search(search);
        } else if (categoryId != null) {
            contents = contentService.getByCategory(categoryId);
        } else {
            contents = contentService.getAllContents();
        }
        return ResponseEntity.ok(ApiResponse.success("Marketplace catalog retrieved successfully", contents));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CatalogResponse>>> search(@RequestParam String keyword) {
        List<CatalogResponse> results = contentService.search(keyword);
        return ResponseEntity.ok(ApiResponse.success("Search results retrieved successfully", results));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<CatalogResponse>>> byCategory(@PathVariable Long categoryId) {
        List<CatalogResponse> results = contentService.getByCategory(categoryId);
        return ResponseEntity.ok(ApiResponse.success("Category content retrieved successfully", results));
    }
}