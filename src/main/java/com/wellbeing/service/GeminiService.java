package com.wellbeing.service;

import com.wellbeing.model.WellnessNudge;
import java.util.concurrent.CompletableFuture;

/**
 * Service interface for AI operations and integrations with Gemini.
 */
public interface GeminiService {

    /**
     * Asynchronously generates a personalized exam-prep wellness nudge.
     *
     * @param user the target user
     * @return CompletableFuture containing the saved WellnessNudge
     */
    CompletableFuture<WellnessNudge> generateNudgeAsync(com.wellbeing.model.User user);
}
