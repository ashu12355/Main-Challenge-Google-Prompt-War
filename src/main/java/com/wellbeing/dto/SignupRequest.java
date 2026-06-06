package com.wellbeing.dto;

import com.wellbeing.model.TargetExam;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @NotNull
    private TargetExam targetExam;

    public SignupRequest() {}

    public SignupRequest(String username, String password, TargetExam targetExam) {
        this.username = username;
        this.password = password;
        this.targetExam = targetExam;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TargetExam getTargetExam() {
        return targetExam;
    }

    public void setTargetExam(TargetExam targetExam) {
        this.targetExam = targetExam;
    }
}
