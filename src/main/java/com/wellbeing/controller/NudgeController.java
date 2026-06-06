package com.wellbeing.controller;

import com.wellbeing.dto.NudgeResponse;
import com.wellbeing.model.WellnessNudge;
import com.wellbeing.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/nudges")
public class NudgeController {

    @Autowired
    private GeminiService geminiService;

    @GetMapping("/personalized")
    public CompletableFuture<ResponseEntity<NudgeResponse>> getPersonalizedNudge() {
        return geminiService.generateNudgeAsync()
                .thenApply(nudge -> {
                    NudgeResponse response = new NudgeResponse(
                            nudge.getId(),
                            nudge.getNudgeText(),
                            nudge.getContextTag(),
                            nudge.getTimestamp()
                    );
                    return ResponseEntity.ok(response);
                });
    }
}
