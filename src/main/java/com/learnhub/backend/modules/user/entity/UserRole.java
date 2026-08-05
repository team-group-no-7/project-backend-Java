package com.learnhub.backend.modules.user.entity;

/**
 * UserRole — Enum representing system user roles.
 * Learner is the base role, Creator is the upgraded role with creator capabilities,
 * and Admin is the platform oversight role.
 */
public enum UserRole {
    LEARNER,
    CREATOR,
    ADMIN
}
