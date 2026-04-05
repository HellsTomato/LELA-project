package com.lela.backend.service;

import com.lela.backend.dto.CourseResponse;
import com.lela.backend.dto.UnitResponse;
import com.lela.backend.entity.Course;
import com.lela.backend.entity.Unit;
import com.lela.backend.repository.CourseRepository;
import com.lela.backend.repository.UnitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Service with read-only course/unit operations.
 */
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UnitRepository unitRepository;

    public CourseService(CourseRepository courseRepository, UnitRepository unitRepository) {
        this.courseRepository = courseRepository;
        this.unitRepository = unitRepository;
    }

    /**
     * Returns all courses for GET /api/courses.
     */
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAllByOrderByIdAsc()
                .stream()
                .map(this::toCourseResponse)
                .toList();
    }

    /**
     * Returns units for a specific course.
     *
     * We validate course existence first to return clean 404 behavior instead of empty
     * arrays for invalid IDs.
     */
    public List<UnitResponse> getUnitsByCourseId(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found: " + courseId);
        }

        return unitRepository.findByCourseIdOrderByPositionAsc(courseId)
                .stream()
                .map(unit -> toUnitResponse(unit, courseId))
                .toList();
    }

    private CourseResponse toCourseResponse(Course course) {
        return new CourseResponse(course.getId(), course.getTitle(), course.getDescription());
    }

    private UnitResponse toUnitResponse(Unit unit, Long courseId) {
        return new UnitResponse(unit.getId(), unit.getTitle(), unit.getPosition(), courseId);
    }
}
