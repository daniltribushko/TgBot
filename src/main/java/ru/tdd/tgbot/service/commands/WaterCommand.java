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
 * Команда для получения списка водоносов
 */
@Component
public class WaterCommand implements BotCommandHandler {
    private final UserRepository userRepository;

    @Autowired
    public WaterCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void action(TelegramLongPollingBot bot, Update update) {
        Message message = update.getMessage();
        String separator = " ";
        userRepository.findByTgId(message.getChatId()).ifPresent(current -> {
            if (BotCommand.WATER.getRoles().contains(current.getRole())) {
                StringBuilder text = new StringBuilder();
                userRepository.findAllByIsActiveIsTrueOrderByOrder().forEach(user -> {
                    text.append("@")
                            .append(user.getUsername())
                            .append(separator)
                            .append("-")
                            .append(separator)
                            .append(user.getFullName() == null ? "" : user.getFullName());
                    if (user.isQueue()) {
                        text.append(separator)
                                .append("-")
                                .append(separator)
                                .append("Его очередь!");
                    }
                    text.append("\n");
                });

                TgMessagesBotUtils.sendMessage(
                        bot,
                        SendMessage.builder()
                                .chatId(message.getChatId())
                                .text(text.toString())
                                .build()
                );
            }
        });
    }
}
