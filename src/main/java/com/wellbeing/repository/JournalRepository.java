package com.wellbeing.repository;

import com.wellbeing.model.Journal;
import com.wellbeing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JournalRepository extends JpaRepository<Journal, Long> {
    List<Journal> findByUserOrderByCreationDateDesc(User user);
}
