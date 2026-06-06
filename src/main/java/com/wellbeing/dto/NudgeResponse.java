package com.wellbeing.dto;

import java.time.LocalDateTime;

public class NudgeResponse {
    private Long id;
    private String nudgeText;
    private String contextTag;
    private LocalDateTime timestamp;

    public NudgeResponse() {}

    public NudgeResponse(Long id, String nudgeText, String contextTag, LocalDateTime timestamp) {
        this.id = id;
        this.nudgeText = nudgeText;
        this.contextTag = contextTag;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNudgeText() {
        return nudgeText;
    }

    public void setNudgeText(String nudgeText) {
        this.nudgeText = nudgeText;
    }

    public String getContextTag() {
        return contextTag;
    }

    public void setContextTag(String contextTag) {
        this.contextTag = contextTag;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
