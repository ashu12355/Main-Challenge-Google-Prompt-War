package com.wellbeing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class JournalRequest {

    @NotBlank
    @Size(max = 10000)
    private String entryText;

    public JournalRequest() {}

    public JournalRequest(String entryText) {
        this.entryText = entryText;
    }

    public String getEntryText() {
        return entryText;
    }

    public void setEntryText(String entryText) {
        this.entryText = entryText;
    }
}
