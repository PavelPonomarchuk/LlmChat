package ru.ponomarchuk.llmchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ponomarchuk.llmchat.entity.Message;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatIdOrderByCreationTimeAsc(Long chatId);
}
