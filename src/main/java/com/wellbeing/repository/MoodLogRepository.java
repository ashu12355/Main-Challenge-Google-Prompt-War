package com.wellbeing.repository;

import com.wellbeing.model.MoodLog;
import com.wellbeing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for MoodLog operations optimized to prevent N+1 queries.
 */
public interface MoodLogRepository extends JpaRepository<MoodLog, Long> {

    /**
     * Finds all mood logs for a user ordered by timestamp descending.
     * Uses JOIN FETCH to load the user eagerly.
     *
     * @param user the user
     * @return list of mood logs
     */
    @Query("SELECT m FROM MoodLog m JOIN FETCH m.user WHERE m.user = :user ORDER BY m.timestamp DESC")
    List<MoodLog> findByUserOrderByTimestampDesc(@Param("user") User user);

    /**
     * Finds all mood logs for a user after a timestamp ordered by timestamp ascending.
     * Uses JOIN FETCH to load the user eagerly.
     *
     * @param user      the user
     * @param timestamp the cutoff timestamp
     * @return list of mood logs
     */
    @Query("SELECT m FROM MoodLog m JOIN FETCH m.user WHERE m.user = :user AND m.timestamp > :timestamp ORDER BY m.timestamp ASC")
    List<MoodLog> findByUserAndTimestampAfterOrderByTimestampAsc(@Param("user") User user, @Param("timestamp") LocalDateTime timestamp);
}
