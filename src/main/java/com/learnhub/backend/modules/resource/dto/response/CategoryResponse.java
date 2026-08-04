package com.learnhub.backend.modules.resource.dto.response;

/**
 * CategoryResponse — DTO representing a resource category.
 * Prevents raw JPA Category entity exposure.
 */
public class CategoryResponse {

    private Long id;
    private String name;
    private Integer resourceCount;

    public CategoryResponse() {
    }

    public CategoryResponse(Long id, String name, Integer resourceCount) {
        this.id = id;
        this.name = name;
        this.resourceCount = resourceCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(Integer resourceCount) {
        this.resourceCount = resourceCount;
    }
}
