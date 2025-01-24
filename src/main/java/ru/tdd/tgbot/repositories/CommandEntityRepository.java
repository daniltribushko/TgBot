package ru.tdd.tgbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tdd.tgbot.models.entities.CommandEntity;

import java.util.Optional;

/**
 * @author Tribushko Danil
 * @since 24.01.2025
 * Репозиторий для работы с сущностями комманд
 */
@Repository
public interface CommandEntityRepository extends JpaRepository<CommandEntity, Long> {
    Optional<CommandEntity> findFirstByUser_TgIdOrderByIdDesc(Long tgId);
}
