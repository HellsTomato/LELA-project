package com.lela.backend.dto;

/**
 * API response model for unit list endpoint.
 */
public class UnitResponse {

    private Long id;
    private String title;
    private Integer position;
    private Long courseId;

    public UnitResponse() {
    }

    public UnitResponse(Long id, String title, Integer position, Long courseId) {
        this.id = id;
        this.title = title;
        this.position = position;
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
