package com.wellbeing.controller;

import com.wellbeing.dto.NudgeResponse;
import com.wellbeing.model.User;
import com.wellbeing.service.GeminiService;
import com.wellbeing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * Controller for personalized wellness advice nudges.
 */
@RestController
@RequestMapping("/api/v1/nudges")
public class NudgeController {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private UserService userService;

    /**
     * Generates and returns a personalized stress relief nudge asynchronously.
     *
     * @return future containing nudge details
     */
    @GetMapping("/personalized")
    public CompletableFuture<ResponseEntity<NudgeResponse>> getPersonalizedNudge() {
        User user = userService.getCurrentUser();
        return geminiService.generateNudgeAsync(user)
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
