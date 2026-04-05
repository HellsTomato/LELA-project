package com.lela.backend.controller;

import com.lela.backend.dto.CourseResponse;
import com.lela.backend.dto.UnitResponse;
import com.lela.backend.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for course and unit endpoints.
 */
@RestController
@RequestMapping("/api")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Required endpoint #1:
     * GET /api/courses
     */
    @GetMapping("/courses")
    public List<CourseResponse> getCourses() {
        return courseService.getAllCourses();
    }

    /**
     * Required endpoint #2:
     * GET /api/courses/{id}/units
     */
    @GetMapping("/courses/{id}/units")
    public List<UnitResponse> getCourseUnits(@PathVariable("id") Long courseId) {
        return courseService.getUnitsByCourseId(courseId);
    }
}
