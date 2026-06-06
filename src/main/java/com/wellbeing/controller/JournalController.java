package com.wellbeing.controller;

import com.wellbeing.dto.JournalDto;
import com.wellbeing.dto.JournalRequest;
import com.wellbeing.model.Journal;
import com.wellbeing.service.JournalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for journal entries and guided prompt endpoints.
 */
@RestController
@RequestMapping("/api/v1/journals")
public class JournalController {

    @Autowired
    private JournalService journalService;

    /**
     * Saves a student reflection journal entry.
     *
     * @param request the journal request payload
     * @return the saved journal details
     */
    @PostMapping
    public ResponseEntity<JournalDto> saveJournal(@Valid @RequestBody JournalRequest request) {
        Journal journal = journalService.saveJournal(request.getEntryText());
        JournalDto dto = new JournalDto(journal.getId(), journal.getEntryText(), journal.getCreationDate());
        return ResponseEntity.ok(dto);
    }

    /**
     * Retrieves all journal entries for the current student.
     *
     * @return list of journal details
     */
    @GetMapping
    public ResponseEntity<List<JournalDto>> getJournals() {
        List<Journal> journals = journalService.getJournals();
        List<JournalDto> dtos = journals.stream()
                .map(j -> new JournalDto(j.getId(), j.getEntryText(), j.getCreationDate()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Retrieves 3 context-aware prompts for the guided journal.
     *
     * @return list of prompt strings
     */
    @GetMapping("/prompts")
    public ResponseEntity<List<String>> getPrompts() {
        return ResponseEntity.ok(journalService.getJournalPrompts());
    }
}
