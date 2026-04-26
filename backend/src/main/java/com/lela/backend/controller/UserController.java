package com.lela.backend.controller;

import com.lela.backend.dto.UserProgressResponse;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import com.lela.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for user endpoints.
 */
@RestController
@RequestMapping("/api")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Returns user progress summary with points, completed lessons, and unlocked rewards.
     * GET /api/users/{id}/progress
     */
    @GetMapping("/users/{id}/progress")
    public UserProgressResponse getUserProgress(@PathVariable("id") @Positive(message = "user id must be positive") Long userId) {
        return userService.getUserProgress(userId);
    }

    /**
     * Reset user progress (delete all completed lessons and reset points/rewards).
     * DELETE /api/users/{id}/progress
     */
    @DeleteMapping("/users/{id}/progress")
    public void resetUserProgress(@PathVariable("id") @Positive(message = "user id must be positive") Long userId) {
        userService.resetUserProgress(userId);
    }
}
