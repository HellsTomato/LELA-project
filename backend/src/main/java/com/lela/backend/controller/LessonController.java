package com.lela.backend.controller;

import com.lela.backend.dto.CompleteLessonRequest;
import com.lela.backend.dto.CompleteLessonResponse;
import com.lela.backend.dto.LessonResponse;
import com.lela.backend.service.LessonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for lesson endpoints.
 */
@RestController
@RequestMapping("/api")
@Validated
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    /**
     * Required endpoint #3:
     * GET /api/lessons/{id}
     */
    @GetMapping("/lessons/{id}")
    public LessonResponse getLesson(@PathVariable("id") @Positive(message = "lesson id must be positive") Long lessonId) {
        return lessonService.getLessonById(lessonId);
    }

    /**
     * Required endpoint #4:
     * POST /api/lessons/{id}/complete
     *
     * Body example:
     * {
     *   "userId": 1
     * }
     */
    @PostMapping("/lessons/{id}/complete")
    public CompleteLessonResponse completeLesson(@PathVariable("id") @Positive(message = "lesson id must be positive") Long lessonId,
                                                 @Valid @RequestBody CompleteLessonRequest request) {
        return lessonService.completeLesson(lessonId, request.getUserId());
    }
}
