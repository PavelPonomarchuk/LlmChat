package ru.ponomarchuk.llmchat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatSendRequest {
    @JsonProperty("chat_id")
    private Long chatId;
    private String message;

    public ChatSendRequest() {
    }

    public ChatSendRequest(Long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
