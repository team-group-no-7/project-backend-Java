package com.learnhub.backend.common.dto;

/**
 * ApiResponse — Standardized JSON response wrapper used by ALL controllers.
 * 
 * Implemented in pure Java without Lombok annotations.
 * Uses explicit getters, setters, constructors, and static factory methods.
 *
 * Every API endpoint returns this JSON structure:
 * {
 *   "success": true/false,
 *   "message": "Human-readable message",
 *   "data": { ... payload ... }
 * }
 */
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    // Default Constructor (Required by Jackson for JSON deserialization)
    public ApiResponse() {
    }

    // Parameterized Constructor
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

    // Static factory method for successful responses
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    // Static factory method for error responses
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
