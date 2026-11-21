package ru.ponomarchuk.llmchat.dto;

import java.util.List;

public class MessagesResponse {
    private List<MessageResponse> messages;

    public MessagesResponse() {
    }

    public MessagesResponse(List<MessageResponse> messages) {
        this.messages = messages;
    }

    public List<MessageResponse> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageResponse> messages) {
        this.messages = messages;
    }
}
