package com.wellbeing.dto;

import java.time.LocalDateTime;

public class JournalDto {
    private Long id;
    private String entryText;
    private LocalDateTime creationDate;

    public JournalDto() {}

    public JournalDto(Long id, String entryText, LocalDateTime creationDate) {
        this.id = id;
        this.entryText = entryText;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntryText() {
        return entryText;
    }

    public void setEntryText(String entryText) {
        this.entryText = entryText;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
