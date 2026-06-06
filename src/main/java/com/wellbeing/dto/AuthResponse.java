package com.wellbeing.dto;

public class AuthResponse {
    private String token;
    private String username;
    private String targetExam;

    public AuthResponse() {}

    public AuthResponse(String token, String username, String targetExam) {
        this.token = token;
        this.username = username;
        this.targetExam = targetExam;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTargetExam() {
        return targetExam;
    }

    public void setTargetExam(String targetExam) {
        this.targetExam = targetExam;
    }
}
