package com.wellbeing.service;

import com.wellbeing.dto.AnalyticsResponse;
import com.wellbeing.dto.MoodLogDto;
import com.wellbeing.model.MoodLog;
import com.wellbeing.model.MoodType;
import com.wellbeing.model.TriggerTag;
import com.wellbeing.model.User;
import com.wellbeing.repository.MoodLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MoodService {

    @Autowired
    private MoodLogRepository moodLogRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public MoodLog logMood(MoodType moodScore, TriggerTag triggerTag) {
        User user = userService.getCurrentUser();
        MoodLog moodLog = new MoodLog(user, moodScore, triggerTag);
        return moodLogRepository.save(moodLog);
    }

    @Transactional(readOnly = true)
    public AnalyticsResponse getAnalytics() {
        User user = userService.getCurrentUser();
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);

        // Fetch all mood logs for the user to display, but aggregate weekly trend
        List<MoodLog> allLogs = moodLogRepository.findByUserOrderByTimestampDesc(user);
        List<MoodLog> weeklyLogs = moodLogRepository.findByUserAndTimestampAfterOrderByTimestampAsc(user, oneWeekAgo);

        List<MoodLogDto> dtos = allLogs.stream()
                .map(log -> new MoodLogDto(log.getId(), log.getMoodScore(), log.getTriggerTag(), log.getTimestamp()))
                .collect(Collectors.toList());

        // Initialize maps with 0 counts for user friendly responses
        Map<MoodType, Long> moodCounts = new EnumMap<>(MoodType.class);
        for (MoodType type : MoodType.values()) {
            moodCounts.put(type, 0L);
        }
        Map<TriggerTag, Long> triggerCounts = new EnumMap<>(TriggerTag.class);
        for (TriggerTag tag : TriggerTag.values()) {
            triggerCounts.put(tag, 0L);
        }

        // Populate counts based on recent weekly logs
        for (MoodLog log : weeklyLogs) {
            moodCounts.put(log.getMoodScore(), moodCounts.getOrDefault(log.getMoodScore(), 0L) + 1);
            triggerCounts.put(log.getTriggerTag(), triggerCounts.getOrDefault(log.getTriggerTag(), 0L) + 1);
        }

        return new AnalyticsResponse(dtos, moodCounts, triggerCounts);
    }
}
