package ru.ponomarchuk.llmchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ponomarchuk.llmchat.entity.Chat;
import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByUserId(Long userId);
}
