package com.lela.backend.repository;

import com.lela.backend.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for units.
 */
public interface UnitRepository extends JpaRepository<Unit, Long> {

    List<Unit> findByCourseIdOrderByPositionAsc(Long courseId);
}
