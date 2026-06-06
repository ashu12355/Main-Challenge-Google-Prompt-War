package com.wellbeing.repository;

import com.wellbeing.model.MoodLog;
import com.wellbeing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface MoodLogRepository extends JpaRepository<MoodLog, Long> {
    List<MoodLog> findByUserOrderByTimestampDesc(User user);
    List<MoodLog> findByUserAndTimestampAfterOrderByTimestampAsc(User user, LocalDateTime timestamp);
}
