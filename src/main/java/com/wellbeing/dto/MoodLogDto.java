package com.wellbeing.dto;

import com.wellbeing.model.MoodType;
import com.wellbeing.model.TriggerTag;
import java.time.LocalDateTime;

public class MoodLogDto {
    private Long id;
    private MoodType mood;
    private TriggerTag triggerTag;
    private LocalDateTime timestamp;

    public MoodLogDto() {}

    public MoodLogDto(Long id, MoodType mood, TriggerTag triggerTag, LocalDateTime timestamp) {
        this.id = id;
        this.mood = mood;
        this.triggerTag = triggerTag;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
