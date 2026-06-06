package com.wellbeing.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing logged mood states and stress triggers.
 */
@Entity
@Table(name = "mood_logs", indexes = {
    @Index(name = "idx_mood_logs_user_timestamp", columnList = "user_id, timestamp")
})
public class MoodLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "mood_score", nullable = false)
    @Enumerated(EnumType.STRING)
    private MoodType moodScore;

    @Column(name = "trigger_tag", nullable = false)
    @Enumerated(EnumType.STRING)
    private TriggerTag triggerTag;

    @Column(name = "check_in_period", nullable = false)
    @Enumerated(EnumType.STRING)
    private CheckInPeriod checkInPeriod = CheckInPeriod.MORNING;

    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }

    public MoodLog() {}

    public MoodLog(User user, MoodType moodScore, TriggerTag triggerTag) {
        this.user = user;
        this.moodScore = moodScore;
        this.triggerTag = triggerTag;
        this.checkInPeriod = CheckInPeriod.MORNING;
    }

    public MoodLog(User user, MoodType moodScore, TriggerTag triggerTag, CheckInPeriod checkInPeriod) {
        this.user = user;
        this.moodScore = moodScore;
        this.triggerTag = triggerTag;
        this.checkInPeriod = checkInPeriod;
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

    public MoodType getMoodScore() {
        return moodScore;
    }

    public void setMoodScore(MoodType moodScore) {
        this.moodScore = moodScore;
    }

    public TriggerTag getTriggerTag() {
        return triggerTag;
    }

    public void setTriggerTag(TriggerTag triggerTag) {
        this.triggerTag = triggerTag;
    }

    public CheckInPeriod getCheckInPeriod() {
        return checkInPeriod;
    }

    public void setCheckInPeriod(CheckInPeriod checkInPeriod) {
        this.checkInPeriod = checkInPeriod;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
