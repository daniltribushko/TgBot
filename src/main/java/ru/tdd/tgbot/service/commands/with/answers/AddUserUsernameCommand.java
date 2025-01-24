package ru.tdd.tgbot.service.commands.with.answers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tdd.tgbot.models.entities.CommandEntity;
import ru.tdd.tgbot.models.entities.User;
import ru.tdd.tgbot.models.enums.Role;
import ru.tdd.tgbot.repositories.CommandEntityRepository;
import ru.tdd.tgbot.repositories.UserRepository;
import ru.tdd.tgbot.utls.TgMessagesBotUtils;

/**
 * @author Tribushko Danil
 * @since 24.01.2025
 * Команда для добавления пользователю имени
 */
@Component
public class AddUserUsernameCommand implements BotCommandWithAnswerHandler {
    private final UserRepository userRepository;
    private final CommandEntityRepository commandEntityRepository;

    @Autowired
    public AddUserUsernameCommand(
            UserRepository userRepository,
            CommandEntityRepository commandEntityRepository
    ) {
        this.userRepository = userRepository;
        this.commandEntityRepository = commandEntityRepository;
    }


    @Override
    public void action(TelegramLongPollingBot bot, Update update, CommandEntity command) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        userRepository.findByTgId(chatId).ifPresent(current -> {
                    String username = message.getText();
                    if (!userRepository.existsByUsername(username)) {
                        User user = new User();
                        user.setUsername(username);
                        user.setActive(false);
                        user.setQueue(false);
                        user.setRole(Role.USER);
                        user.setOrder(userRepository.getLasOrder() + 1);

                        userRepository.save(user);

                        TgMessagesBotUtils.sendMessage(
                                bot,
                                SendMessage
                                        .builder()
                                        .text("Пользователь: " + user.getUsername() + " добавлен")
                                        .chatId(chatId)
                                        .build()
                        );
                        commandEntityRepository.delete(command);
                    } else {
                        TgMessagesBotUtils.sendMessage(
                                bot,
                                SendMessage.builder()
                                        .chatId(chatId)
                                        .text("Пользователь с именем: " + username + " уже сохранен")
                                        .build()
                        );
                        commandEntityRepository.delete(command);
                    }

                    TgMessagesBotUtils.sendMessage(
                            bot,
                            DeleteMessage
                                    .builder()
                                    .chatId(chatId)
                                    .messageId(message.getMessageId())
                                    .build()
                    );

                    TgMessagesBotUtils.sendMessage(
                            bot,
                            DeleteMessage
                                    .builder()
                                    .chatId(chatId)
                                    .messageId(command.getBotMessageId())
                                    .build()
                    );
                }
        );
    }
}
