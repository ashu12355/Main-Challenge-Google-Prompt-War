package com.wellbeing.service;

import com.wellbeing.dto.AnalyticsResponse;
import com.wellbeing.dto.WeeklyReportResponse;
import com.wellbeing.model.CheckInPeriod;
import com.wellbeing.model.MoodLog;
import com.wellbeing.model.MoodType;
import com.wellbeing.model.TriggerTag;

import java.util.concurrent.CompletableFuture;

/**
 * Service interface for Mood and Stress operations.
 */
public interface MoodService {

    /**
     * Logs the current student stress and mood state.
     *
     * @param moodScore  the mood score
     * @param triggerTag the trigger tag
     * @return the saved MoodLog entity
     */
    MoodLog logMood(MoodType moodScore, TriggerTag triggerTag);

    /**
     * Logs the current student stress and mood state with check-in period.
     *
     * @param moodScore      the mood score
     * @param triggerTag     the trigger tag
     * @param checkInPeriod the check-in period
     * @return the saved MoodLog entity
     */
    MoodLog logMood(MoodType moodScore, TriggerTag triggerTag, CheckInPeriod checkInPeriod);

    /**
     * Compiles recent log analytics.
     *
     * @return compiled analytics details
     */
    AnalyticsResponse getAnalytics();

    /**
     * Aggregates weekly mood trends, triggers, journal status asynchronously.
     *
     * @param user the target user
     * @return a future holding structured weekly report details
     */
    CompletableFuture<WeeklyReportResponse> getWeeklyReportAsync(com.wellbeing.model.User user);
}
