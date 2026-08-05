package com.learnhub.backend.modules.user.service.impl;

import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.common.util.SecurityUtils;
import com.learnhub.backend.modules.user.dto.AdminUserResponse;
import com.learnhub.backend.modules.user.dto.UpdateProfileRequest;
import com.learnhub.backend.modules.user.dto.UserProfileResponse;
import com.learnhub.backend.modules.user.entity.User;
import com.learnhub.backend.modules.user.repository.UserRepository;
import com.learnhub.backend.modules.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UserServiceImpl — Implementation class for User Profile & User Management with SLF4J logging.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Fetching all users in the system");
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminUserResponse> searchUsers(String search) {
        log.info("Searching admin users with filter: '{}'", search);
        List<User> users = userRepository.searchUsers(search);
        return users.stream().map(this::mapToAdminUserResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AdminUserResponse toggleUserFreeze(Long userId) {
        log.info("Toggling user freeze status for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if ("FROZEN".equalsIgnoreCase(user.getStatus()) || "SUSPENDED".equalsIgnoreCase(user.getStatus())) {
            user.setStatus("ACTIVE");
        } else {
            user.setStatus("FROZEN");
        }

        User savedUser = userRepository.saveAndFlush(user);
        log.info("Successfully updated user ID: {} status to: {}", savedUser.getId(), savedUser.getStatus());
        return mapToAdminUserResponse(savedUser);
    }

    private AdminUserResponse mapToAdminUserResponse(User user) {
        AdminUserResponse response = new AdminUserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setStatus(user.getStatus());
        response.setJoinedAt(user.getJoinedAt());
        return response;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserProfileResponse getUserProfile(Long userId) {
        log.info("Fetching user profile for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        SecurityUtils.validateOwnership(user.getEmail());
        return mapToProfileResponse(user);
    }

    @Override
    @Transactional
    public UserProfileResponse updateUserProfile(Long userId, UpdateProfileRequest request) {
        log.info("Updating user profile for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        SecurityUtils.validateOwnership(user.getEmail());

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
        log.info("Successfully updated user profile for user ID: {}", updatedUser.getId());
        return mapToProfileResponse(updatedUser);
    }

    @Override
    @Transactional
    public UserProfileResponse becomeCreator(Long userId) {
        log.info("Upgrading user ID: {} to CREATOR role", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        SecurityUtils.validateOwnership(user.getEmail());

        if (user.getUserRole() != com.learnhub.backend.modules.user.entity.UserRole.CREATOR) {
            user.setUserRole(com.learnhub.backend.modules.user.entity.UserRole.CREATOR);
        }

        User updatedUser = userRepository.save(user);
        log.info("Successfully upgraded user ID: {} to role: {}", updatedUser.getId(), updatedUser.getRole());
        return mapToProfileResponse(updatedUser);
    }

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
