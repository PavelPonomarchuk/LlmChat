package ru.ponomarchuk.llmchat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.ponomarchuk.llmchat.dto.*;
import ru.ponomarchuk.llmchat.exception.ForbiddenException;
import ru.ponomarchuk.llmchat.service.ChatService;

@RestController
@RequestMapping("/chats")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<ChatsResponse> getChats(@PathVariable("user_id") Long userId,
                                                  Authentication authentication) {
        Long authUserId = (Long) authentication.getPrincipal();
        if (!authUserId.equals(userId)) {
            return ResponseEntity.status(403).build();
        }
        ChatsResponse response = new ChatsResponse(chatService.getChats(userId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/chat/{chat_id}")
    public ResponseEntity<MessagesResponse> getChat(@PathVariable("chat_id") Long chatId,
                                                    Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        try {
            MessagesResponse response = chatService.getChatMessages(chatId, userId);
            return ResponseEntity.ok(response);
        } catch (ForbiddenException e) {
            return ResponseEntity.status(403).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping("/chat/new")
    public ResponseEntity<MessageResponse> createChat(@RequestBody ChatNewRequest request,
                                                      Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        MessageResponse response = chatService.createChat(userId, request.getMessage());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/chat/send")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody ChatSendRequest request,
                                                       Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        try {
            MessageResponse response = chatService.sendMessage(request.getChatId(), userId, request.getMessage());
            return ResponseEntity.ok(response);
        } catch (ForbiddenException e) {
            return ResponseEntity.status(403).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }
}
