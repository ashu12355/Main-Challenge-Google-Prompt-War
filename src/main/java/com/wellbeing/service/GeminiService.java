package com.wellbeing.service;

import com.wellbeing.model.MoodLog;
import com.wellbeing.model.User;
import com.wellbeing.model.WellnessNudge;
import com.wellbeing.repository.MoodLogRepository;
import com.wellbeing.repository.WellnessNudgeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class GeminiService {

    @Autowired
    private WellnessNudgeRepository nudgeRepository;

    @Autowired
    private MoodLogRepository moodLogRepository;

    @Autowired
    private UserService userService;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key:}")
    private String apiKey;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Async
    public CompletableFuture<WellnessNudge> generateNudgeAsync() {
        User user = userService.getCurrentUser();
        List<MoodLog> recentMoods = moodLogRepository.findByUserOrderByTimestampDesc(user);

        // Construct context-aware prompt localized for Indian students
        String targetExamName = user.getTargetExam().name();
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("You are a supportive student counselor. Generate a concise, 2-sentence wellness advice/nudge (maximum 40 words) for an Indian student preparing for ")
                .append(targetExamName)
                .append(" under typical household or hostel pressures.");

        String contextTag = "GENERAL";
        if (!recentMoods.isEmpty()) {
            MoodLog latest = recentMoods.get(0);
            contextTag = latest.getMoodScore().name() + "_" + latest.getTriggerTag().name();
            promptBuilder.append(" Their emotional state is ")
                    .append(latest.getMoodScore().name())
                    .append(" and their stress trigger is ")
                    .append(latest.getTriggerTag().name())
                    .append(". Empathize with this and suggest a specific localized break or coping action (e.g. taking a 5-minute walk on the terrace, listening to lo-fi Bollywood beats, having a hot cup of cutting chai, or doing 2 minutes of Anulom Vilom breathing).");
        } else {
            promptBuilder.append(" Suggest a practical tip like taking a short walk on the terrace, listening to Bollywood lo-fi beats, having cutting chai, or doing Anulom Vilom to manage study workload.");
        }

        String prompt = promptBuilder.toString();
        String nudgeText;

        if (apiKey == null || apiKey.trim().isEmpty()) {
            nudgeText = generateMockNudge(user, recentMoods);
        } else {
            try {
                nudgeText = callGeminiApi(prompt);
            } catch (Exception e) {
                // Fallback on error to ensure robustness
                nudgeText = generateMockNudge(user, recentMoods);
            }
        }

        WellnessNudge nudge = new WellnessNudge(user, nudgeText, contextTag);
        WellnessNudge savedNudge = nudgeRepository.save(nudge);
        return CompletableFuture.completedFuture(savedNudge);
    }

    private String callGeminiApi(String prompt) throws Exception {
        String requestBody = objectMapper.writeValueAsString(new GeminiPayload(prompt));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "?key=" + apiKey))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonNode root = objectMapper.readTree(response.body());
            JsonNode textNode = root.path("candidates").get(0).path("content").path("parts").get(0).path("text");
            if (!textNode.isMissingNode()) {
                return textNode.asText().trim();
            }
        }
        throw new RuntimeException("Failed to call Gemini API: HTTP " + response.statusCode() + " -> " + response.body());
    }

    private String generateMockNudge(User user, List<MoodLog> recentMoods) {
        String exam = user.getTargetExam().name();
        if (recentMoods.isEmpty()) {
            return "Preparing for " + exam + " can feel intense, but consistency is key. Remember to divide your syllabus, take a short terrace walk, or have a cup of hot cutting chai.";
        }
        MoodLog latest = recentMoods.get(0);
        switch (latest.getMoodScore()) {
            case HIGH_MOTIVATION:
                return "Fod denge! 🚀 Love this high motivation for your " + exam + " prep. Tackle your toughest revision slots now, and celebrate with a cup of hot cutting chai later.";
            case ANXIOUS:
                return "Feeling thoda tension? 😟 Don't let your " + latest.getTriggerTag().name().replace("_", " ") + " overwhelm you. Try doing 2 minutes of Anulom Vilom breathing or take a 5-minute walk on the terrace to center yourself.";
            case FRUSTRATED:
                return "Dimag kharab hai? 🤯 Exam pressure can feel very heavy. Turn on some lo-fi Bollywood beats, take a deep breath, and step away from the screen for 5 minutes.";
            case BURNED_OUT:
                return "Thak gaya hu? 🛌 Step away from your desk completely. Grab a quick snack, talk to a hostel friend, or take a short nap. Rest is vital during this marathon!";
            default:
                return "Keep moving forward step by step on your " + exam + " journey. You have what it takes to manage this workload.";
        }
    }

    // Static helper classes for request mapping
    public static class GeminiPayload {
        public List<Content> contents;

        public GeminiPayload(String prompt) {
            this.contents = List.of(new Content(prompt));
        }

        public static class Content {
            public List<Part> parts;

            public Content(String text) {
                this.parts = List.of(new Part(text));
            }
        }

        public static class Part {
            public String text;

            public Part(String text) {
                this.text = text;
            }
        }
    }
}
