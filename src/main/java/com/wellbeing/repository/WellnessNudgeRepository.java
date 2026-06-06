package com.wellbeing.repository;

import com.wellbeing.model.User;
import com.wellbeing.model.WellnessNudge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for WellnessNudge operations optimized to prevent N+1 queries.
 */
public interface WellnessNudgeRepository extends JpaRepository<WellnessNudge, Long> {

    /**
     * Finds all wellness nudges for a user ordered by timestamp descending.
     * Uses JOIN FETCH to eagerly load user details.
     *
     * @param user the user
     * @return list of wellness nudges
     */
    @Query("SELECT w FROM WellnessNudge w JOIN FETCH w.user WHERE w.user = :user ORDER BY w.timestamp DESC")
    List<WellnessNudge> findByUserOrderByTimestampDesc(@Param("user") User user);
}
