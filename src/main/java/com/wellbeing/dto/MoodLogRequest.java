package com.wellbeing.dto;

import com.wellbeing.model.CheckInPeriod;
import com.wellbeing.model.MoodType;
import com.wellbeing.model.TriggerTag;
import jakarta.validation.constraints.NotNull;

/**
 * Data transfer object for mood logging request payloads.
 */
public class MoodLogRequest {

    @NotNull
    private MoodType mood;

    @NotNull
    private TriggerTag triggerTag;

    private CheckInPeriod checkInPeriod = CheckInPeriod.MORNING;

    public MoodLogRequest() {}

    public MoodLogRequest(MoodType mood, TriggerTag triggerTag) {
        this.mood = mood;
        this.triggerTag = triggerTag;
        this.checkInPeriod = CheckInPeriod.MORNING;
    }

    public MoodLogRequest(MoodType mood, TriggerTag triggerTag, CheckInPeriod checkInPeriod) {
        this.mood = mood;
        this.triggerTag = triggerTag;
        this.checkInPeriod = checkInPeriod;
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
}
