package com.lela.backend.entity;

import jakarta.persistence.*;

/**
 * Reward entity.
 *
 * Defines a real-world or in-app prize that opens when a user reaches a points threshold.
 */
@Entity
@Table(name = "reward")
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, length = 2000)
    private String description;

    /**
     * Minimum total points required to unlock this reward.
     */
    @Column(nullable = false)
    private Integer requiredPoints;

    public Reward() {
    }

    public Reward(String name, String description, Integer requiredPoints) {
        this.name = name;
        this.description = description;
        this.requiredPoints = requiredPoints;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRequiredPoints() {
        return requiredPoints;
    }

    public void setRequiredPoints(Integer requiredPoints) {
        this.requiredPoints = requiredPoints;
    }
}
