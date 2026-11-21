package ru.ponomarchuk.llmchat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageResponse {
    private Long id;

    @JsonProperty("chat_id")
    private Long chatId;

    @JsonProperty("creation_time")
    private Long creationTime;

    private String text;

    @JsonProperty("is_question")
    private Boolean isQuestion;

    public MessageResponse() {
    }

    public MessageResponse(Long id, Long chatId, Long creationTime, String text, Boolean isQuestion) {
        this.id = id;
        this.chatId = chatId;
        this.creationTime = creationTime;
        this.text = text;
        this.isQuestion = isQuestion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getIsQuestion() {
        return isQuestion;
    }

    public void setIsQuestion(Boolean isQuestion) {
        this.isQuestion = isQuestion;
    }
}
