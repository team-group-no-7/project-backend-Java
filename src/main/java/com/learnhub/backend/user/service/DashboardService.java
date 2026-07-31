package com.learnhub.backend.user.service;

import com.learnhub.backend.user.dto.response.DashboardResponse;

public interface DashboardService {

    DashboardResponse getDashboard(Long userId);

}