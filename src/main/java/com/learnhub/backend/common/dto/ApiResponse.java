package com.learnhub.backend.common.dto;

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
 *
 * IMPLEMENTATION NOTE (CDAC PGCP-AC):
 * - Implemented in pure Java without Lombok to demonstrate core Java encapsulation principles.
 */
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    /**
     * Default Constructor (Required by Jackson for JSON deserialization)
     */
    public ApiResponse() {
    }

    /**
     * Parameterized Constructor
     */
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * Factory method for successful responses.
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    /**
     * Factory method for error responses (no data payload).
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
