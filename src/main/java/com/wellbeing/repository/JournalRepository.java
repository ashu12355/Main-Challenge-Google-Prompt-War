package com.wellbeing.repository;

import com.wellbeing.model.Journal;
import com.wellbeing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for Journal operations optimized to prevent N+1 queries.
 */
public interface JournalRepository extends JpaRepository<Journal, Long> {

    /**
     * Finds all journal entries for a user ordered by creation date descending.
     * Uses JOIN FETCH to eagerly load user details.
     *
     * @param user the user
     * @return list of journal entries
     */
    @Query("SELECT j FROM Journal j JOIN FETCH j.user WHERE j.user = :user ORDER BY j.creationDate DESC")
    List<Journal> findByUserOrderByCreationDateDesc(@Param("user") User user);
}
