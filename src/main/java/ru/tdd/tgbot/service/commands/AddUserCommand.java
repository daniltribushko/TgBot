package ru.tdd.tgbot.service.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tdd.tgbot.models.entities.CommandEntity;
import ru.tdd.tgbot.models.enums.BotCommand;
import ru.tdd.tgbot.models.enums.BotCommandWithAnswer;
import ru.tdd.tgbot.repositories.CommandEntityRepository;
import ru.tdd.tgbot.repositories.UserRepository;
import ru.tdd.tgbot.utls.TgMessagesBotUtils;

/**
 * @author Tribushko Danil
 * @since 24.01.2025
 * Команда, для добавления пользователя
 */
@Component
public class AddUserCommand implements BotCommandHandler {
    private final UserRepository userRepository;
    private final CommandEntityRepository commandEntityRepository;

    @Autowired
    public AddUserCommand(UserRepository userRepository, CommandEntityRepository commandEntityRepository) {
        this.userRepository = userRepository;
        this.commandEntityRepository = commandEntityRepository;
    }

    @Override
    public void action(TelegramLongPollingBot bot, Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        userRepository.findByTgId(chatId).ifPresent(current -> {
            if (BotCommand.START.getRoles().contains(current.getRole())) {

                SendMessage sendMessage =  SendMessage.builder()
                        .chatId(chatId)
                        .text("Введите имя пользователя")
                        .build();

                CommandEntity command =
                        new CommandEntity()
                                .builder()
                                .user(current)
                                .botMessageId(TgMessagesBotUtils.sendMessage(bot, sendMessage))
                                .body(null)
                                .command(BotCommandWithAnswer.ADD_USER_USERNAME)
                                .build();

                commandEntityRepository.save(command);
            }
        });
    }
}
