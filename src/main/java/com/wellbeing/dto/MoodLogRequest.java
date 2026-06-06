package com.wellbeing.dto;

import com.wellbeing.model.MoodType;
import com.wellbeing.model.TriggerTag;
import jakarta.validation.constraints.NotNull;

public class MoodLogRequest {

    @NotNull
    private MoodType mood;

    @NotNull
    private TriggerTag triggerTag;

    public MoodLogRequest() {}

    public MoodLogRequest(MoodType mood, TriggerTag triggerTag) {
        this.mood = mood;
        this.triggerTag = triggerTag;
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
}
