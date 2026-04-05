package com.lela.backend.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * User entity for MVP.
 *
 * For demonstration purposes we store only the fields needed by the current flow:
 * identity, profile basics, total points, and unlocked rewards.
 *
 * Note: we map to table name app_user because "user" is a reserved keyword in PostgreSQL.
 */
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Display name shown in UI.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Simple unique identifier for demo (no auth integration in MVP).
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Total gamification points user has earned.
     */
    @Column(nullable = false)
    private int points = 0;

    /**
     * Rewards already unlocked by this user.
     *
     * Many-to-many is enough for MVP and avoids creating a separate UserReward entity.
     */
    @ManyToMany
    @JoinTable(
            name = "user_reward_unlock",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "reward_id")
    )
    private Set<Reward> unlockedRewards = new HashSet<>();

    public User() {
    }

    public User(String name, String email, int points) {
        this.name = name;
        this.email = email;
        this.points = points;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Set<Reward> getUnlockedRewards() {
        return unlockedRewards;
    }

    public void setUnlockedRewards(Set<Reward> unlockedRewards) {
        this.unlockedRewards = unlockedRewards;
    }
}
