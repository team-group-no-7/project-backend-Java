package com.learnhub.backend.modules.user.service;

import com.learnhub.backend.modules.user.dto.response.DashboardResponse;

public interface DashboardService {

    DashboardResponse getDashboard(Long userId);

}