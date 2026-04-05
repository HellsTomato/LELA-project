package com.lela.backend.repository;

import com.lela.backend.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for reward thresholds.
 */
public interface RewardRepository extends JpaRepository<Reward, Long> {

    List<Reward> findByRequiredPointsLessThanEqualOrderByRequiredPointsAsc(Integer points);
}
