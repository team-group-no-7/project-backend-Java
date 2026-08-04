package com.learnhub.backend.modules.resource.controller;

import com.learnhub.backend.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * CatalogController — Placeholder status endpoint for Resource Catalog & Content Studio Module.
 */
@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<String>> getStatus() {
        return ResponseEntity.ok(ApiResponse.success("Catalog & Content Studio Module is Active", "OK"));
    }
}
