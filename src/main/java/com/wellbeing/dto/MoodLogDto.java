package com.wellbeing.dto;

import com.wellbeing.model.CheckInPeriod;
import com.wellbeing.model.MoodType;
import com.wellbeing.model.TriggerTag;
import java.time.LocalDateTime;

/**
 * Data transfer object for mood log details.
 */
public class MoodLogDto {
    private Long id;
    private MoodType mood;
    private TriggerTag triggerTag;
    private CheckInPeriod checkInPeriod;
    private LocalDateTime timestamp;

    public MoodLogDto() {}

    public MoodLogDto(Long id, MoodType mood, TriggerTag triggerTag, LocalDateTime timestamp) {
        this.id = id;
        this.mood = mood;
        this.triggerTag = triggerTag;
        this.checkInPeriod = CheckInPeriod.MORNING;
        this.timestamp = timestamp;
    }

    public MoodLogDto(Long id, MoodType mood, TriggerTag triggerTag, CheckInPeriod checkInPeriod, LocalDateTime timestamp) {
        this.id = id;
        this.mood = mood;
        this.triggerTag = triggerTag;
        this.checkInPeriod = checkInPeriod;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MoodType getMood() {
        return mood;
    }

    public void setMood(MoodType mood) {
        this.mood = mood;
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
