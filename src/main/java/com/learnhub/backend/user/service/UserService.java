package com.learnhub.backend.user.service;

import com.learnhub.backend.user.dto.UpdateProfileRequest;
import com.learnhub.backend.user.dto.UserProfileResponse;
import com.learnhub.backend.user.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * UserService — Business logic interface for User Profile Management.
 */
public interface UserService {

    /**
     * Get all users in the system.
     */
    List<User> getAllUsers();

    /**
     * Get user by ID.
     */
    Optional<User> getUserById(Long id);

    /**
     * Get user by email.
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Fetch user profile by ID.
     */
    UserProfileResponse getUserProfile(Long userId);

    /**
     * Update user profile information.
     */
    UserProfileResponse updateUserProfile(Long userId, UpdateProfileRequest request);

    /**
     * Upgrade a user to CREATOR role while retaining existing roles (e.g. "LEARNER,CREATOR").
     */
    UserProfileResponse becomeCreator(Long userId);
}
