package ru.tdd.tgbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;
import ru.tdd.tgbot.models.entities.User;

import java.util.Optional;

/**
 * @author Tribushko Danil
 * @since 21.01.2025
 * Репозиторий для работы с пользователем
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByIsQueueTrue();
    @NativeQuery("SELECT \"order\" FROM users ORDER BY \"order\" DESC LIMIT 1")
    Integer getLasOrder();
    Optional<User> findByTgId(Long tgId);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
