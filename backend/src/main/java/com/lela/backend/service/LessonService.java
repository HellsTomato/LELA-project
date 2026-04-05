package com.lela.backend.service;

import com.lela.backend.dto.CompleteLessonResponse;
import com.lela.backend.dto.LessonResponse;
import com.lela.backend.entity.Lesson;
import com.lela.backend.entity.Progress;
import com.lela.backend.entity.Reward;
import com.lela.backend.entity.User;
import com.lela.backend.repository.LessonRepository;
import com.lela.backend.repository.ProgressRepository;
import com.lela.backend.repository.RewardRepository;
import com.lela.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service that contains lesson-related read logic and completion game mechanics.
 */
@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final ProgressRepository progressRepository;
    private final RewardRepository rewardRepository;

    public LessonService(LessonRepository lessonRepository,
                         UserRepository userRepository,
                         ProgressRepository progressRepository,
                         RewardRepository rewardRepository) {
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
        this.progressRepository = progressRepository;
        this.rewardRepository = rewardRepository;
    }

    /**
     * Returns full lesson details for GET /api/lessons/{id}.
     */
    @Transactional
    public LessonResponse getLessonById(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found: " + lessonId));

        return new LessonResponse(
                lesson.getId(),
                lesson.getTitle(),
                lesson.getContent(),
                lesson.getPointsReward(),
                lesson.getUnit().getId(),
                lesson.getUnit().getTitle(),
                lesson.getUnit().getCourse().getId()
        );
    }

    /**
     * Main MVP gamification flow for POST /api/lessons/{id}/complete:
     * 1) ensure user and lesson exist,
     * 2) mark lesson as completed if not completed before,
     * 3) add points,
     * 4) unlock any rewards whose threshold is reached,
     * 5) return result payload with rewardUnlocked flag.
     */
    @Transactional
    public CompleteLessonResponse completeLesson(Long lessonId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + userId));

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found: " + lessonId));

        Optional<Progress> existingProgress = progressRepository.findByUserIdAndLessonId(userId, lessonId);
        if (existingProgress.isPresent()) {
            return new CompleteLessonResponse(
                    "Lesson was already completed earlier",
                    user.getId(),
                    lesson.getId(),
                    0,
                    user.getPoints(),
                    false,
                    extractSortedRewardNames(user.getUnlockedRewards())
            );
        }

        // Persist completion fact first so lesson completion is stored even if reward logic changes later.
        Progress progress = new Progress(user, lesson, LocalDateTime.now());
        progressRepository.save(progress);

        int pointsEarned = lesson.getPointsReward() == null ? 0 : lesson.getPointsReward();
        user.setPoints(user.getPoints() + pointsEarned);

        // Load all rewards that should be available at the new points total.
        List<Reward> eligibleRewards = rewardRepository.findByRequiredPointsLessThanEqualOrderByRequiredPointsAsc(user.getPoints());
        Set<Long> alreadyUnlockedRewardIds = user.getUnlockedRewards()
                .stream()
                .map(Reward::getId)
                .collect(Collectors.toSet());

        boolean rewardUnlocked = false;
        for (Reward reward : eligibleRewards) {
            if (!alreadyUnlockedRewardIds.contains(reward.getId())) {
                user.getUnlockedRewards().add(reward);
                rewardUnlocked = true;
            }
        }

        userRepository.save(user);

        return new CompleteLessonResponse(
                "Lesson completed successfully",
                user.getId(),
                lesson.getId(),
                pointsEarned,
                user.getPoints(),
                rewardUnlocked,
                extractSortedRewardNames(user.getUnlockedRewards())
        );
    }

    private List<String> extractSortedRewardNames(Set<Reward> rewards) {
        return rewards.stream()
                .map(Reward::getName)
                .sorted(Comparator.naturalOrder())
                .toList();
    }
}
