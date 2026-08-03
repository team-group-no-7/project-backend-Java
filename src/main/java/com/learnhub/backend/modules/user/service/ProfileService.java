package com.learnhub.backend.modules.user.service;

import com.learnhub.backend.modules.user.dto.UpdateProfileRequest;
import com.learnhub.backend.modules.user.dto.response.ProfileResponse;

public interface ProfileService {

    ProfileResponse getProfile(Long userId);

    ProfileResponse updateProfile(Long userId,
                                  UpdateProfileRequest request);

}