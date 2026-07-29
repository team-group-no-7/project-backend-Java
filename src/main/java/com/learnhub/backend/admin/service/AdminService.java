package com.learnhub.backend.admin.service;

import com.learnhub.backend.admin.dto.AdminUserResponse;
import com.learnhub.backend.admin.dto.PlatformStatsResponse;
import com.learnhub.backend.catalog.dto.ContentResponse;
import java.util.List;

public interface AdminService {

    PlatformStatsResponse getPlatformStats();

    List<AdminUserResponse> searchUsers(String search);

    AdminUserResponse toggleUserFreeze(Long userId);

    ContentResponse toggleContentStatus(Long contentId, String newStatus);

    List<ContentResponse> getAllContents();
}
