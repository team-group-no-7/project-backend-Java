package com.learnhub.backend.modules.resource.controller;

import com.learnhub.backend.modules.resource.dto.response.ContentReaderResponse;
import com.learnhub.backend.modules.resource.dto.response.CatalogResponse;
import com.learnhub.backend.modules.resource.service.ContentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ContentController — REST Controller for Learner Catalog Browsing and Content Reader APIs.
 * Implemented in pure Java with explicit constructor injection (no Lombok).
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
    public ContentReaderResponse getContent(@PathVariable Long id) {
        return contentService.getContent(id);
    }

    @GetMapping
    public List<CatalogResponse> allContents() {
        return contentService.getAllContents();
    }

    @GetMapping("/search")
    public List<CatalogResponse> search(@RequestParam String keyword) {
        return contentService.search(keyword);
    }

    @GetMapping("/category/{categoryId}")
    public List<CatalogResponse> byCategory(@PathVariable Long categoryId) {
        return contentService.getByCategory(categoryId);
    }
}