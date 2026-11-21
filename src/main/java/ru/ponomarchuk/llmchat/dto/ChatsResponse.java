package ru.ponomarchuk.llmchat.dto;

import java.util.List;

public class ChatsResponse {
    private List<ChatResponse> chats;

    public ChatsResponse() {
    }

    public ChatsResponse(List<ChatResponse> chats) {
        this.chats = chats;
    }

    public List<ChatResponse> getChats() {
        return chats;
    }

    public void setChats(List<ChatResponse> chats) {
        this.chats = chats;
    }
}
