package com.lela.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Progress entity.
 *
 * Stores a fact that a specific user completed a specific lesson at a certain time.
 */
@Entity
@Table(
        name = "progress",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "lesson_id"})
)
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    /**
     * Date and time when completion was recorded.
     */
    @Column(nullable = false)
    private LocalDateTime completedAt;

    public Progress() {
    }

    public Progress(User user, Lesson lesson, LocalDateTime completedAt) {
        this.user = user;
        this.lesson = lesson;
        this.completedAt = completedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
