package com.learnhub.backend.user.service.impl;

import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.user.dto.UpdateProfileRequest;
import com.learnhub.backend.user.dto.UserProfileResponse;
import com.learnhub.backend.user.entity.User;
import com.learnhub.backend.user.repository.UserRepository;
import com.learnhub.backend.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/*
 * UserServiceImpl — Implementation class for User Profile Management.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // Explicit constructor for dependency injection
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
     * Get all users in the system.
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /*
     * Get user by ID.
     */
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /*
     * Get user by email.
     */
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /*
     * Fetch user profile by ID.
     */
    @Override
    public UserProfileResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return mapToProfileResponse(user);
    }

    /*
     * Update user profile information.
     * Updates name, headline, location, and avatarUrl.
     */
    @Override
    @Transactional
    public UserProfileResponse updateUserProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Update fields if provided
        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            user.setName(request.getName());
        }
        if (request.getHeadline() != null) {
            user.setHeadline(request.getHeadline());
        }
        if (request.getLocation() != null) {
            user.setLocation(request.getLocation());
        }
        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }

        User updatedUser = userRepository.save(user);
        return mapToProfileResponse(updatedUser);
    }

    /*
     * Upgrade a user to CREATOR role while retaining existing roles (e.g. "LEARNER,CREATOR").
     * Updates PostgreSQL database column `role` to preserve both LEARNER and CREATOR access.
     */
    @Override
    @Transactional
    public UserProfileResponse becomeCreator(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        String currentRole = user.getRole();
        if (currentRole == null || currentRole.trim().isEmpty()) {
            user.setRole("LEARNER,CREATOR");
        } else if (!currentRole.contains("CREATOR")) {
            user.setRole(currentRole + ",CREATOR");
        }

        User updatedUser = userRepository.save(user);
        return mapToProfileResponse(updatedUser);
    }

    /*
     * Helper method to map User entity to UserProfileResponse DTO.
     */
    private UserProfileResponse mapToProfileResponse(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getAvatarUrl(),
                user.getHeadline(),
                user.getLocation(),
                user.getJoinedAt()
        );
    }
}
