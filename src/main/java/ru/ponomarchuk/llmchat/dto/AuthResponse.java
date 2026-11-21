package ru.ponomarchuk.llmchat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponse {
    private String status;
    private String message;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("access_token")
    private String accessToken;

    public AuthResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
