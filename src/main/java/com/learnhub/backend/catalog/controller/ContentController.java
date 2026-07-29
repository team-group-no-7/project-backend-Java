package com.learnhub.backend.catalog.controller;

import com.learnhub.backend.catalog.dto.response.ContentReaderResponse;
import com.learnhub.backend.catalog.dto.response.CatalogResponse;
import com.learnhub.backend.catalog.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @GetMapping("/{id}")
    public ContentReaderResponse getContent(@PathVariable Long id) {
        return contentService.getContent(id);
    }
    @GetMapping
    public List<CatalogResponse> allContents(){

        return contentService.getAllContents();

    }
    @GetMapping("/search")
    public List<CatalogResponse> search(
            @RequestParam String keyword){

        return contentService.search(keyword);

    }

    @GetMapping("/category/{categoryId}")
    public List<CatalogResponse> byCategory(@PathVariable Long categoryId){

        return contentService.getByCategory(categoryId);

    }
}