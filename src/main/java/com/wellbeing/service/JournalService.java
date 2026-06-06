package com.wellbeing.service;

import com.wellbeing.model.Journal;
import com.wellbeing.model.User;
import com.wellbeing.repository.JournalRepository;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public Journal saveJournal(String entryText) {
        User user = userService.getCurrentUser();

        // XSS Prevention: Sanitize input by stripping all HTML/Script tags
        String sanitizedText = Jsoup.clean(entryText, Safelist.none());

        Journal journal = new Journal(user, sanitizedText);
        return journalRepository.save(journal);
    }

    @Transactional(readOnly = true)
    public List<Journal> getJournals() {
        User user = userService.getCurrentUser();
        return journalRepository.findByUserOrderByCreationDateDesc(user);
    }
}
