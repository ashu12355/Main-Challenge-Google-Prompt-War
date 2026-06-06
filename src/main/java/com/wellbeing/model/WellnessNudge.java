package com.wellbeing.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing AI-generated personalized wellness nudges.
 */
@Entity
@Table(name = "wellness_nudges", indexes = {
    @Index(name = "idx_wellness_nudges_user_timestamp", columnList = "user_id, timestamp")
})
public class WellnessNudge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "nudge_text", nullable = false, columnDefinition = "TEXT")
    private String nudgeText;

    @Column(name = "context_tag", nullable = false)
    private String contextTag;

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }

    public WellnessNudge() {}

    public WellnessNudge(User user, String nudgeText, String contextTag) {
        this.user = user;
        this.nudgeText = nudgeText;
        this.contextTag = contextTag;
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

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
