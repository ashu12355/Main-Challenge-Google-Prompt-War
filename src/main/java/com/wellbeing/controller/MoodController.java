package com.wellbeing.controller;

import com.wellbeing.dto.AnalyticsResponse;
import com.wellbeing.dto.MoodLogDto;
import com.wellbeing.dto.MoodLogRequest;
import com.wellbeing.dto.WeeklyReportResponse;
import com.wellbeing.model.MoodLog;
import com.wellbeing.model.User;
import com.wellbeing.service.MoodService;
import com.wellbeing.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

/**
 * Controller for student mood tracking and analytics endpoints.
 */
@RestController
@RequestMapping("/api/v1/moods")
public class MoodController {

    @Autowired
    private MoodService moodService;

    @Autowired
    private UserService userService;

    /**
     * Logs the current student stress and mood state.
     *
     * @param request the mood log request payload
     * @return the logged mood details
     */
    @PostMapping
    public ResponseEntity<MoodLogDto> logMood(@Valid @RequestBody MoodLogRequest request) {
        MoodLog log = moodService.logMood(request.getMood(), request.getTriggerTag(), request.getCheckInPeriod());
        MoodLogDto dto = new MoodLogDto(log.getId(), log.getMoodScore(), log.getTriggerTag(), log.getCheckInPeriod(), log.getTimestamp());
        return ResponseEntity.ok(dto);
    }

    /**
     * Retrieves recent mood trends and trigger analytics.
     *
     * @return the compiled analytics
     */
    @GetMapping("/analytics")
    public ResponseEntity<AnalyticsResponse> getAnalytics() {
        return ResponseEntity.ok(moodService.getAnalytics());
    }

    /**
     * Generates a Sunday ritual weekly progress report response asynchronously.
     *
     * @return future holding the weekly report
     */
    @GetMapping("/weekly-report")
    public CompletableFuture<ResponseEntity<WeeklyReportResponse>> getWeeklyReport() {
        User user = userService.getCurrentUser();
        return moodService.getWeeklyReportAsync(user)
                .thenApply(ResponseEntity::ok);
    }
}
