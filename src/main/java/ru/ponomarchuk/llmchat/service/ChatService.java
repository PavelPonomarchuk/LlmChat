package ru.ponomarchuk.llmchat.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import ru.ponomarchuk.llmchat.dto.*;
import ru.ponomarchuk.llmchat.entity.Chat;
import ru.ponomarchuk.llmchat.entity.Message;
import ru.ponomarchuk.llmchat.exception.ForbiddenException;
import ru.ponomarchuk.llmchat.repository.ChatRepository;
import ru.ponomarchuk.llmchat.repository.MessageRepository;

import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final ChatClient chatClient;

    public ChatService(ChatRepository chatRepository,
                       MessageRepository messageRepository,
                       ChatClient chatClient) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.chatClient = chatClient;
    }

    public List<ChatResponse> getChats(Long userId) {
        List<Chat> chats = chatRepository.findByUserId(userId);
        return chats.stream()
                .map(chat -> new ChatResponse(chat.getId(), chat.getTitle()))
                .toList();
    }

    public MessagesResponse getChatMessages(Long chatId, Long userId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        if (!chat.getUserId().equals(userId)) {
            throw new ForbiddenException();
        }

        List<Message> messages = messageRepository.findByChatIdOrderByCreationTimeAsc(chatId);
        List<MessageResponse> messageResponses = messages.stream()
                .map(m -> new MessageResponse(m.getId(), m.getChatId(), m.getCreationTime(),
                        m.getMessageText(), m.getIsQuestion()))
                .toList();

        return new MessagesResponse(messageResponses);
    }

    public MessageResponse createChat(Long userId, String message) {
        String title = message.length() > 30 ? message.substring(0, 30) : message;

        Chat chat = new Chat();
        chat.setUserId(userId);
        chat.setTitle(title);
        chatRepository.save(chat);

        Message userMessage = new Message();
        userMessage.setChatId(chat.getId());
        userMessage.setCreationTime(System.currentTimeMillis());
        userMessage.setMessageText(message);
        userMessage.setIsQuestion(true);
        messageRepository.save(userMessage);

        String llmResponse = callLlm(message);

        Message llmMessage = new Message();
        llmMessage.setChatId(chat.getId());
        llmMessage.setCreationTime(System.currentTimeMillis());
        llmMessage.setMessageText(llmResponse);
        llmMessage.setIsQuestion(false);
        messageRepository.save(llmMessage);

        return new MessageResponse(llmMessage.getId(), chat.getId(), llmMessage.getCreationTime(),
                llmResponse, false);
    }

    public MessageResponse sendMessage(Long chatId, Long userId, String message) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        if (!chat.getUserId().equals(userId)) {
            throw new ForbiddenException();
        }

        Message userMessage = new Message();
        userMessage.setChatId(chatId);
        userMessage.setCreationTime(System.currentTimeMillis());
        userMessage.setMessageText(message);
        userMessage.setIsQuestion(true);
        messageRepository.save(userMessage);

        String llmResponse = callLlm(message);

        Message llmMessage = new Message();
        llmMessage.setChatId(chatId);
        llmMessage.setCreationTime(System.currentTimeMillis());
        llmMessage.setMessageText(llmResponse);
        llmMessage.setIsQuestion(false);
        messageRepository.save(llmMessage);

        return new MessageResponse(llmMessage.getId(), chatId, llmMessage.getCreationTime(),
                llmResponse, false);
    }

    private String callLlm(String message) {
        try {
            return chatClient.prompt()
                    .user(message)
                    .call()
                    .content();
        } catch (Exception e) {
            return "Ошибка при получении ответа от LLM: " + e.getMessage();
        }
    }
}
