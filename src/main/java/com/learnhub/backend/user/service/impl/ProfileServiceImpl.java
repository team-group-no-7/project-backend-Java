package com.learnhub.backend.user.service.impl;

import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.user.entity.User;
import com.learnhub.backend.user.repository.UserRepository;
import com.learnhub.backend.user.dto.request.UpdateProfileRequest;
import com.learnhub.backend.user.dto.response.ProfileResponse;
import com.learnhub.backend.user.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public ProfileResponse getProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return map(user);
    }

    @Override
    @Transactional
    public ProfileResponse updateProfile(Long userId,
                                         UpdateProfileRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        user.setName(request.getName());
        user.setHeadline(request.getHeadline());
        user.setLocation(request.getLocation());
        user.setAvatarUrl(request.getAvatarUrl());

        userRepository.save(user);

        return map(user);
    }

    private ProfileResponse map(User user) {

        return ProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .avatarUrl(user.getAvatarUrl())
                .headline(user.getHeadline())
                .location(user.getLocation())
                .build();
    }
}