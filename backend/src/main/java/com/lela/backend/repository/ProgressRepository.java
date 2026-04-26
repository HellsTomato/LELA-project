package com.lela.backend.repository;

import com.lela.backend.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository for lesson completion records.
 */
public interface ProgressRepository extends JpaRepository<Progress, Long> {

    @Query("SELECT p FROM Progress p WHERE p.user.id = :userId AND p.lesson.id = :lessonId")
    Optional<Progress> findByUserIdAndLessonId(@Param("userId") Long userId, @Param("lessonId") Long lessonId);

    @Query("""
            SELECT p
            FROM Progress p
            JOIN FETCH p.lesson l
            JOIN FETCH l.unit u
            JOIN FETCH u.course c
            WHERE p.user.id = :userId
            ORDER BY p.completedAt DESC
            """)
    List<Progress> findDetailedByUserIdOrderByCompletedAtDesc(@Param("userId") Long userId);

    void deleteByUserId(Long userId);
}
