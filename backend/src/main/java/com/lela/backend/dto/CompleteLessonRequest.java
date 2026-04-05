package com.lela.backend.dto;

import jakarta.validation.constraints.NotNull;

/**
 * API request body for lesson completion endpoint.
 *
 * MVP simplification: user identity comes in request body instead of full auth.
 */
public class CompleteLessonRequest {

    @NotNull(message = "userId is required")
    private Long userId;

    public CompleteLessonRequest() {
    }

    public CompleteLessonRequest(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
