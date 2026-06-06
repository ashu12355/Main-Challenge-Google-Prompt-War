package com.wellbeing.dto;

import com.wellbeing.model.MoodType;
import com.wellbeing.model.TriggerTag;
import java.util.List;
import java.util.Map;

public class AnalyticsResponse {
    private List<MoodLogDto> logs;
    private Map<MoodType, Long> moodCounts;
    private Map<TriggerTag, Long> triggerCounts;

    public AnalyticsResponse() {}

    public AnalyticsResponse(List<MoodLogDto> logs, Map<MoodType, Long> moodCounts, Map<TriggerTag, Long> triggerCounts) {
        this.logs = logs;
        this.moodCounts = moodCounts;
        this.triggerCounts = triggerCounts;
    }

    public List<MoodLogDto> getLogs() {
        return logs;
    }

    public void setLogs(List<MoodLogDto> logs) {
        this.logs = logs;
    }

    public Map<MoodType, Long> getMoodCounts() {
        return moodCounts;
    }

    public void setMoodCounts(Map<MoodType, Long> moodCounts) {
        this.moodCounts = moodCounts;
    }

    public Map<TriggerTag, Long> getTriggerCounts() {
        return triggerCounts;
    }

    public void setTriggerCounts(Map<TriggerTag, Long> triggerCounts) {
        this.triggerCounts = triggerCounts;
    }
}
