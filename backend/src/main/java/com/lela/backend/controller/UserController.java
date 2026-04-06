package com.lela.backend.controller;

import com.lela.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for user endpoints.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Reset user progress (delete all completed lessons and reset points/rewards).
     * DELETE /api/users/{id}/progress
     */
    @DeleteMapping("/users/{id}/progress")
    public void resetUserProgress(@PathVariable("id") Long userId) {
        userService.resetUserProgress(userId);
    }
}
