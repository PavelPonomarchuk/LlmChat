package ru.ponomarchuk.llmchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ponomarchuk.llmchat.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserLogin(String userLogin);
    boolean existsByUserLogin(String userLogin);
}
