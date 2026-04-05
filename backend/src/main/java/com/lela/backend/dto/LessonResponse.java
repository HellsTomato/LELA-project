package com.lela.backend.dto;

/**
 * API response model for lesson details endpoint.
 */
public class LessonResponse {

    private Long id;
    private String title;
    private String content;
    private Integer pointsReward;
    private Long unitId;
    private String unitTitle;
    private Long courseId;

    public LessonResponse() {
    }

    public LessonResponse(Long id,
                          String title,
                          String content,
                          Integer pointsReward,
                          Long unitId,
                          String unitTitle,
                          Long courseId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.pointsReward = pointsReward;
        this.unitId = unitId;
        this.unitTitle = unitTitle;
        this.courseId = courseId;
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

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitTitle() {
        return unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
