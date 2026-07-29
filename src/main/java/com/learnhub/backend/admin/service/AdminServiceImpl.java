package com.learnhub.backend.admin.service;

import com.learnhub.backend.admin.dto.AdminUserResponse;
import com.learnhub.backend.admin.dto.PlatformStatsResponse;
import com.learnhub.backend.catalog.dto.ContentResponse;
import com.learnhub.backend.catalog.entity.Content;
import com.learnhub.backend.catalog.repository.ContentRepository;
import com.learnhub.backend.user.entity.User;
import com.learnhub.backend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Override
    @Transactional(readOnly = true)
    public PlatformStatsResponse getPlatformStats() {
        PlatformStatsResponse stats = new PlatformStatsResponse();
        stats.setTotalUsers(userRepository.count());
        stats.setTotalContents(contentRepository.count());
        
        // Mock revenue until Billing module is active
        stats.setTotalRevenue(BigDecimal.valueOf(14200.00));
        return stats;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminUserResponse> searchUsers(String search) {
        List<User> users = userRepository.searchUsers(search);
        return users.stream().map(this::mapToUserResponse).collect(Collectors.toList());
    }

    @Override
    public AdminUserResponse toggleUserFreeze(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        // Toggle ACTIVE <-> FROZEN
        if ("ACTIVE".equalsIgnoreCase(user.getStatus())) {
            user.setStatus("FROZEN");
        } else {
            user.setStatus("ACTIVE");
        }
        
        User savedUser = userRepository.save(user);
        return mapToUserResponse(savedUser);
    }

    @Override
    public ContentResponse toggleContentStatus(Long contentId, String newStatus) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Content not found with id: " + contentId));
        
        content.setApprovalStatus(newStatus); // APPROVED, FLAGGED, PENDING
        Content savedContent = contentRepository.save(content);
        return mapToContentResponse(savedContent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentResponse> getAllContents() {
        List<Content> contents = contentRepository.findAll();
        return contents.stream().map(this::mapToContentResponse).collect(Collectors.toList());
    }

    // Mappers
    private AdminUserResponse mapToUserResponse(User user) {
        AdminUserResponse response = new AdminUserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setStatus(user.getStatus());
        response.setJoinedAt(user.getJoinedAt());
        return response;
    }

    private ContentResponse mapToContentResponse(Content content) {
        ContentResponse response = new ContentResponse();
        response.setId(content.getId());
        response.setTitle(content.getTitle());
        response.setDescription(content.getDescription());
        response.setPreviewText(content.getPreviewText());
        response.setContentBody(content.getContentBody());
        response.setFileUrl(content.getFileUrl());
        response.setPrice(content.getPrice());
        response.setType(content.getType());
        response.setLevel(content.getLevel());
        response.setTags(content.getTags());
        response.setFeatured(content.isFeatured());
        response.setTrending(content.isTrending());
        response.setRating(content.getRating());
        response.setReviewsCount(content.getReviewsCount());
        response.setLearnersCount(content.getLearnersCount());
        response.setApprovalStatus(content.getApprovalStatus());
        response.setCreatorId(content.getCreatorId());
        response.setCreatedAt(content.getCreatedAt());
        
        if (content.getCategory() != null) {
            response.setCategoryName(content.getCategory().getName());
        }
        return response;
    }
}
