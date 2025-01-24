package ru.tdd.tgbot.service.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.tdd.tgbot.models.dto.InlineButtonCommandDTO;
import ru.tdd.tgbot.models.enums.BotCommand;
import ru.tdd.tgbot.models.enums.InlineButtonCommand;
import ru.tdd.tgbot.repositories.UserRepository;
import ru.tdd.tgbot.utls.TgMessagesBotUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tribushko Danil
 * @since 24.01.2025
 * Команда для получения списка пользователей
 */
@Component
public class UsersCommand implements BotCommandHandler {
    @Value("${telegram.bot.commands.users.buttons-in-row}")
    private Integer buttonsCount;
    private final UserRepository userRepository;

    @Autowired
    public UsersCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void action(TelegramLongPollingBot bot, Update update) {
        Message message = update.getMessage();
        userRepository.findByTgId(message.getChatId()).ifPresent(user -> {
                    if (BotCommand.USERS.getRoles().contains(user.getRole())) {
                        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                        List<List<InlineKeyboardButton>> keyboards = new ArrayList<>();
                        List<InlineKeyboardButton> row = new ArrayList<>();

                        userRepository.findAll().forEach(u -> {
                            if (keyboards.size() == buttonsCount) {
                                keyboards.add(row);
                                row.clear();
                            }

                            InlineButtonCommandDTO userCommand = new InlineButtonCommandDTO(
                                    InlineButtonCommand.USER,
                                    u.getId()
                            );

                            InlineKeyboardButton button = new InlineKeyboardButton();
                            button.setText(u.getUsername());
                            button.setCallbackData(userCommand.toJson());
                            row.add(button);
                        });

                        if (!row.isEmpty()) {
                            keyboards.add(row);
                        }

                        inlineKeyboardMarkup.setKeyboard(keyboards);

                        TgMessagesBotUtils.sendMessage(
                                bot,
                                SendMessage.builder()
                                        .chatId(message.getChatId())
                                        .text("Выберите пользователя")
                                        .replyMarkup(inlineKeyboardMarkup)
                                        .build()
                        );
                    }
                }
        );
    }
}
