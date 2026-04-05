package com.lela.backend.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * API response body for lesson completion endpoint.
 */
public class CompleteLessonResponse {

    private String message;
    private Long userId;
    private Long lessonId;
    private int pointsEarned;
    private int totalPoints;
    private boolean rewardUnlocked;
    private List<String> unlockedRewards = new ArrayList<>();

    public CompleteLessonResponse() {
    }

    public CompleteLessonResponse(String message,
                                  Long userId,
                                  Long lessonId,
                                  int pointsEarned,
                                  int totalPoints,
                                  boolean rewardUnlocked,
                                  List<String> unlockedRewards) {
        this.message = message;
        this.userId = userId;
        this.lessonId = lessonId;
        this.pointsEarned = pointsEarned;
        this.totalPoints = totalPoints;
        this.rewardUnlocked = rewardUnlocked;
        this.unlockedRewards = unlockedRewards;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public int getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(int pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public boolean isRewardUnlocked() {
        return rewardUnlocked;
    }

    public void setRewardUnlocked(boolean rewardUnlocked) {
        this.rewardUnlocked = rewardUnlocked;
    }

    public List<String> getUnlockedRewards() {
        return unlockedRewards;
    }

    public void setUnlockedRewards(List<String> unlockedRewards) {
        this.unlockedRewards = unlockedRewards;
    }
}
