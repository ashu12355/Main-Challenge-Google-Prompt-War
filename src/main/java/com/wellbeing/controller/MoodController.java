package com.wellbeing.controller;

import com.wellbeing.dto.AnalyticsResponse;
import com.wellbeing.dto.MoodLogDto;
import com.wellbeing.dto.MoodLogRequest;
import com.wellbeing.model.MoodLog;
import com.wellbeing.service.MoodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/moods")
public class MoodController {

    @Autowired
    private MoodService moodService;

    @PostMapping
    public ResponseEntity<MoodLogDto> logMood(@Valid @RequestBody MoodLogRequest request) {
        MoodLog log = moodService.logMood(request.getMood(), request.getTriggerTag());
        MoodLogDto dto = new MoodLogDto(log.getId(), log.getMoodScore(), log.getTriggerTag(), log.getTimestamp());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/analytics")
    public ResponseEntity<AnalyticsResponse> getAnalytics() {
        AnalyticsResponse response = moodService.getAnalytics();
        return ResponseEntity.ok(response);
    }
}
