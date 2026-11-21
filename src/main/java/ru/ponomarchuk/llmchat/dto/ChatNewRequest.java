package ru.ponomarchuk.llmchat.dto;

public class ChatNewRequest {
    private String message;

    public ChatNewRequest() {
    }

    public ChatNewRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
