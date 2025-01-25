package ru.tdd.tgbot.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tdd.tgbot.repositories.CommandEntityRepository;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 25.01.2025
 * Запланированные задачи для работы с сущностями комманд
 */
@Component
public class CommandEntityScheduler {
    private final CommandEntityRepository commandEntityRepository;

    @Autowired
    public CommandEntityScheduler(CommandEntityRepository commandEntityRepository) {
        this.commandEntityRepository = commandEntityRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void deleteCommands() {
        LocalDateTime now = LocalDateTime.now();
        commandEntityRepository.findAll().forEach(commandEntity -> {
            LocalDateTime created = commandEntity.getCreated();
            if (
                    now.getYear() == created.getYear() &&
                            now.getDayOfYear() == created.getDayOfYear() &&
                            now.getHour() == created.getHour() &&
                            now.getMinute() - created.getMinute() > 10
            ) {
                commandEntityRepository.delete(commandEntity);
            }
        });
    }
}
