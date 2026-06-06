package com.wellbeing.repository;

import com.wellbeing.model.User;
import com.wellbeing.model.WellnessNudge;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WellnessNudgeRepository extends JpaRepository<WellnessNudge, Long> {
    List<WellnessNudge> findByUserOrderByTimestampDesc(User user);
}
