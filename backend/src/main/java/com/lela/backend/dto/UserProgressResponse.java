package com.lela.backend.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * API response body for GET /api/users/{id}/progress.
 */
public class UserProgressResponse {

    private Long userId;
    private int totalPoints;
    private int completedLessonsCount;
    private List<CompletedLessonProgressItem> completedLessons = new ArrayList<>();
    private List<RewardProgressItem> unlockedRewards = new ArrayList<>();

    public UserProgressResponse() {
    }

    public UserProgressResponse(Long userId,
                                int totalPoints,
                                int completedLessonsCount,
                                List<CompletedLessonProgressItem> completedLessons,
                                List<RewardProgressItem> unlockedRewards) {
        this.userId = userId;
        this.totalPoints = totalPoints;
        this.completedLessonsCount = completedLessonsCount;
        this.completedLessons = completedLessons;
        this.unlockedRewards = unlockedRewards;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getCompletedLessonsCount() {
        return completedLessonsCount;
    }

    public void setCompletedLessonsCount(int completedLessonsCount) {
        this.completedLessonsCount = completedLessonsCount;
    }

    public List<CompletedLessonProgressItem> getCompletedLessons() {
        return completedLessons;
    }

    public void setCompletedLessons(List<CompletedLessonProgressItem> completedLessons) {
        this.completedLessons = completedLessons;
    }

    public List<RewardProgressItem> getUnlockedRewards() {
        return unlockedRewards;
    }

    public void setUnlockedRewards(List<RewardProgressItem> unlockedRewards) {
        this.unlockedRewards = unlockedRewards;
    }
}