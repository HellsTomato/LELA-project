package com.lela.backend.dto;

import java.time.LocalDateTime;

/**
 * One completed lesson row in user progress response.
 */
public class CompletedLessonProgressItem {

    private Long lessonId;
    private String lessonTitle;
    private Long unitId;
    private Long courseId;
    private int pointsEarned;
    private LocalDateTime completedAt;

    public CompletedLessonProgressItem() {
    }

    public CompletedLessonProgressItem(Long lessonId,
                                       String lessonTitle,
                                       Long unitId,
                                       Long courseId,
                                       int pointsEarned,
                                       LocalDateTime completedAt) {
        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
        this.unitId = unitId;
        this.courseId = courseId;
        this.pointsEarned = pointsEarned;
        this.completedAt = completedAt;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public int getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(int pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}