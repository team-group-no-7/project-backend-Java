package com.learnhub.backend.modules.user.service;

import com.learnhub.backend.modules.user.dto.AdminUserResponse;
import com.learnhub.backend.modules.user.dto.UpdateProfileRequest;
import com.learnhub.backend.modules.user.dto.UserProfileResponse;
import com.learnhub.backend.modules.user.entity.User;

import java.util.List;
import java.util.Optional;

/*
 * UserService — Business logic interface for User Profile & User Management.
 */
public interface UserService {

    /*
     * Get all users in the system.
     */
    List<User> getAllUsers();

    /*
     * Search and list users for administration.
     */
    List<AdminUserResponse> searchUsers(String search);

    /*
     * Toggle user freeze state (ACTIVE <-> FROZEN).
     */
    AdminUserResponse toggleUserFreeze(Long userId);

    /*
     * Update user role by administrator.
     */
    AdminUserResponse updateUserRole(Long userId, String role);

    /*
     * Get user by ID.
     */
    Optional<User> getUserById(Long id);

    /*
     * Get user by email.
     */
    Optional<User> getUserByEmail(String email);

    /*
     * Fetch user profile by ID.
     */
    UserProfileResponse getUserProfile(Long userId);

    /*
     * Update user profile information.
     */
    UserProfileResponse updateUserProfile(Long userId, UpdateProfileRequest request);

    /*
     * Upgrade a user to CREATOR role while retaining existing roles (e.g. "LEARNER,CREATOR").
     */
    UserProfileResponse becomeCreator(Long userId);
}
