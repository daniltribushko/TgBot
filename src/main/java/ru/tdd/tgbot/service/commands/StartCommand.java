package ru.tdd.tgbot.service.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.tdd.tgbot.repositories.UserRepository;
import ru.tdd.tgbot.utls.TgMessagesBotUtils;

/**
 * @author Tribushko Danil
 * @since 21.01.2025
 */
@Component
public class StartCommand implements BotCommandHandler {
    private final UserRepository userRepository;

    @Autowired
    public StartCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void action(TelegramLongPollingBot bot, Update update) {
        Message message = update.getMessage();
        Long tgId = message.getChatId();
        User from  = message.getFrom();
        userRepository.findByUsername(message.getFrom().getUserName()).ifPresent(user -> {
            user.setTgId(tgId);
            user.setFullName(from.getLastName() + " " + from.getFirstName());
            userRepository.save(user);
            TgMessagesBotUtils.sendMessage(
                    bot,
                    SendMessage
                            .builder()
                            .chatId(tgId)
                            .text("Добро пожаловать!")
                            .build()
            );
        });
    }
}
