package com.lela.backend.repository;

import com.lela.backend.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for lesson data.
 */
public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
