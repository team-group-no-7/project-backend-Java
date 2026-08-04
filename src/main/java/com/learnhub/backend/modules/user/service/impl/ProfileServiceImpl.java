package com.learnhub.backend.modules.user.service.impl;

import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.common.util.SecurityUtils;
import com.learnhub.backend.modules.user.entity.User;
import com.learnhub.backend.modules.user.repository.UserRepository;
import com.learnhub.backend.modules.user.dto.UpdateProfileRequest;
import com.learnhub.backend.modules.user.dto.response.ProfileResponse;
import com.learnhub.backend.modules.user.service.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ProfileServiceImpl — Implementation class for Learner Profile Service.
 * Implemented in pure Java with explicit constructor injection (no Lombok).
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    public ProfileServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileResponse getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        SecurityUtils.validateOwnership(user.getEmail());
        return map(user);
    }

    @Override
    @Transactional
    public ProfileResponse updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        SecurityUtils.validateOwnership(user.getEmail());

        user.setName(request.getName());
        user.setHeadline(request.getHeadline());
        user.setLocation(request.getLocation());
        user.setAvatarUrl(request.getAvatarUrl());

        userRepository.save(user);

        return map(user);
    }

    private ProfileResponse map(User user) {
        return new ProfileResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getAvatarUrl(),
                user.getHeadline(),
                user.getLocation()
        );
    }
}