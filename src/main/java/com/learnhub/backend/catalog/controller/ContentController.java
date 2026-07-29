package com.learnhub.backend.catalog.controller;

import com.learnhub.backend.catalog.dto.response.ContentResponse;
import com.learnhub.backend.catalog.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @GetMapping
    public List<ContentResponse> getAllContents() {
        return contentService.getAllContents();
    }

    @GetMapping("/featured")
    public List<ContentResponse> getFeaturedContents() {
        return contentService.getFeaturedContents();
    }

    @GetMapping("/trending")
    public List<ContentResponse> getTrendingContents() {
        return contentService.getTrendingContents();
    }

    @GetMapping("/{id}")
    public ContentResponse getContent(@PathVariable Long id) {
        return contentService.getContentById(id);
    }
}