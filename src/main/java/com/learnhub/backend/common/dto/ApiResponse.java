package com.learnhub.backend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ApiResponse — Standardized JSON response wrapper used by ALL controllers.
 *
 * Every API endpoint in LearnHub returns this structure:
 * {
 *   "success": true/false,
 *   "message": "Human-readable message",
 *   "data": { ... actual payload ... }
 * }
 *
 * WHY THIS EXISTS:
 * - Without this, each controller returns different response shapes.
 * - Frontend developers can always check response.success to determine if the API call worked.
 * - Consistent error handling across all modules.
 *
 * USAGE:
 *   return ResponseEntity.ok(ApiResponse.success("Login successful", authResponse));
 *   return ResponseEntity.badRequest().body(ApiResponse.error("Email already registered"));
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    /**
     * Factory method for successful responses.
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Factory method for error responses (no data payload).
     */
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .data(null)
                .build();
    }
}
