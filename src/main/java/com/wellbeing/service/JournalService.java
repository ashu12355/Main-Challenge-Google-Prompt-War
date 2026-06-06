package com.wellbeing.service;

import com.wellbeing.model.Journal;
import java.util.List;

/**
 * Service interface for Student Reflection Journal operations.
 */
public interface JournalService {

    /**
     * Sanitizes and saves a student reflection journal entry.
     *
     * @param entryText the journal text
     * @return the saved Journal entity
     */
    Journal saveJournal(String entryText);

    /**
     * Retrieves all saved journal entries for the current authenticated user.
     *
     * @return list of journal entries
     */
    List<Journal> getJournals();

    /**
     * Generates 3 dynamic, context-aware prompts for the guided reflection journal.
     *
     * @return list of 3 prompt strings
     */
    List<String> getJournalPrompts();
}
