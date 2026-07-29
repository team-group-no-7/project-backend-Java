package com.learnhub.backend.user.service;

import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.user.dto.UpdateProfileRequest;
import com.learnhub.backend.user.dto.UserProfileResponse;
import com.learnhub.backend.user.entity.User;
import com.learnhub.backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * UserService — Business logic for User Profile Management.
 *
 * Handles fetching and updating user profiles, roles, and identity details.
 * Implemented in pure Java with explicit constructor dependency injection (no Lombok).
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    // Explicit constructor for dependency injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get all users in the system.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get user by ID.
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Get user by email.
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Fetch user profile by ID.
     * Throws ResourceNotFoundException if user does not exist.
     *
     * @param userId the user ID to fetch profile for
     * @return UserProfileResponse DTO
     */
    public UserProfileResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return mapToProfileResponse(user);
    }

    /**
     * Update user profile information.
     * Updates name, headline, location, and avatarUrl.
     *
     * @param userId the ID of the user to update
     * @param request the profile update form fields
     * @return updated UserProfileResponse DTO
     */
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

    /**
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
