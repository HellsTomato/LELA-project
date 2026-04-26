package com.lela.backend.dto;

/**
 * One unlocked reward row in user progress response.
 */
public class RewardProgressItem {

    private Long rewardId;
    private String name;
    private String description;
    private Integer requiredPoints;

    public RewardProgressItem() {
    }

    public RewardProgressItem(Long rewardId, String name, String description, Integer requiredPoints) {
        this.rewardId = rewardId;
        this.name = name;
        this.description = description;
        this.requiredPoints = requiredPoints;
    }

    public Long getRewardId() {
        return rewardId;
    }

    public void setRewardId(Long rewardId) {
        this.rewardId = rewardId;
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