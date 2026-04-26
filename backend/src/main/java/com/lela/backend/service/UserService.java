package com.lela.backend.service;

import com.lela.backend.dto.CompletedLessonProgressItem;
import com.lela.backend.dto.RewardProgressItem;
import com.lela.backend.dto.UserProgressResponse;
import com.lela.backend.entity.Progress;
import com.lela.backend.entity.Reward;
import com.lela.backend.entity.User;
import com.lela.backend.repository.ProgressRepository;
import com.lela.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProgressRepository progressRepository;

    public UserService(UserRepository userRepository, ProgressRepository progressRepository) {
        this.userRepository = userRepository;
        this.progressRepository = progressRepository;
    }

    @Transactional
    public UserProgressResponse getUserProgress(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + userId));

        List<Progress> progressRows = progressRepository.findDetailedByUserIdOrderByCompletedAtDesc(userId);

        List<CompletedLessonProgressItem> completedLessons = progressRows.stream()
                .map(progress -> new CompletedLessonProgressItem(
                        progress.getLesson().getId(),
                        progress.getLesson().getTitle(),
                        progress.getLesson().getUnit().getId(),
                        progress.getLesson().getUnit().getCourse().getId(),
                        progress.getLesson().getPointsReward() == null ? 0 : progress.getLesson().getPointsReward(),
                        progress.getCompletedAt()
                ))
                .toList();

        List<RewardProgressItem> unlockedRewards = user.getUnlockedRewards().stream()
                .sorted(Comparator
                        .comparing(Reward::getRequiredPoints)
                        .thenComparing(Reward::getName))
                .map(reward -> new RewardProgressItem(
                        reward.getId(),
                        reward.getName(),
                        reward.getDescription(),
                        reward.getRequiredPoints()
                ))
                .toList();

        return new UserProgressResponse(
                user.getId(),
                user.getPoints(),
                completedLessons.size(),
                completedLessons,
                unlockedRewards
        );
    }

    @Transactional
    public void resetUserProgress(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + userId));

        progressRepository.deleteByUserId(userId);

        user.setPoints(0);

        user.getUnlockedRewards().clear();

        userRepository.save(user);
    }
}
