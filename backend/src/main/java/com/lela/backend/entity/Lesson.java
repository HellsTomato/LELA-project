package com.lela.backend.entity;

import jakarta.persistence.*;

/**
 * Lesson entity.
 *
 * The lesson is the smallest educational item. Completing a lesson gives points.
 */
@Entity
@Table(name = "lesson")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    /**
     * Text/markdown content of the lesson. In MVP we keep it as a plain text field.
     */
    @Column(nullable = false, length = 5000)
    private String content;

    /**
     * Points granted when the user completes this lesson.
     */
    @Column(nullable = false)
    private Integer pointsReward;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    public Lesson() {
    }

    public Lesson(String title, String content, Integer pointsReward, Unit unit) {
        this.title = title;
        this.content = content;
        this.pointsReward = pointsReward;
        this.unit = unit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPointsReward() {
        return pointsReward;
    }

    public void setPointsReward(Integer pointsReward) {
        this.pointsReward = pointsReward;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
