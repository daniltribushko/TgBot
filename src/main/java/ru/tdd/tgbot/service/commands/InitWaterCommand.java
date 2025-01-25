package ru.tdd.tgbot.service.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tdd.tgbot.models.enums.BotCommand;
import ru.tdd.tgbot.repositories.UserRepository;
import ru.tdd.tgbot.utls.TgMessagesBotUtils;

/**
 * @author Tribushko Danil
 * @since 25.01.2025
 * Команда для инициализации очереди водоносов
 */
@Component
public class InitWaterCommand implements BotCommandHandler {
    private final UserRepository userRepository;

    @Autowired
    public InitWaterCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void action(TelegramLongPollingBot bot, Update update) {
        Message message = update.getMessage();
        userRepository.findByTgId(message.getChatId()).ifPresent(current -> {
            if (BotCommand.INIT_WATER.getRoles().contains(current.getRole())) {
                if (!userRepository.existsByIsQueueTrue()) {
                    userRepository.findFirstByIsQueueFalseAndIsActiveTrueOrderByOrder().ifPresent(user -> {
                        user.setQueue(true);
                        userRepository.save(user);

                        TgMessagesBotUtils.sendMessage(
                                bot,
                                SendMessage.builder()
                                        .chatId(message.getChatId())
                                        .text("Очередь проинициализирована")
                                        .build()
                        );
                    });
                }
            }
        });
    }
}
