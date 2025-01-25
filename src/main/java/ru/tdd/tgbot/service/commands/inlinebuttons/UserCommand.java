package ru.tdd.tgbot.service.commands.inlinebuttons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.MaybeInaccessibleMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.tdd.tgbot.models.dto.InlineButtonCommandDTO;
import ru.tdd.tgbot.models.enums.InlineButtonCommand;
import ru.tdd.tgbot.models.enums.Role;
import ru.tdd.tgbot.repositories.UserRepository;
import ru.tdd.tgbot.utls.TgMessagesBotUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tribushko Danil
 * @since 25.01.2025
 */
@Component
public class UserCommand implements InlineButtonCommandHandler {
    private final UserRepository userRepository;

    @Autowired
    public UserCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void action(TelegramLongPollingBot bot, CallbackQuery callbackQuery, InlineButtonCommandDTO commandDTO) {
        MaybeInaccessibleMessage message = callbackQuery.getMessage();
        Long chatId = message.getChatId();

        userRepository.findByTgId(chatId).ifPresent(current -> {
            if (InlineButtonCommand.USER.getRoles().contains(current.getRole())) {
                userRepository.findById(commandDTO.getUserId()).ifPresent(user -> {
                    if (user.getRole() != Role.ADMIN || current.getRole() == Role.ADMIN) {
                        List<InlineKeyboardButton> row = new ArrayList<>();
                        Integer messageId = message.getMessageId();
                        if (user.getTgId() != null) {
                            row.add(InlineKeyboardButton.builder()
                                    .text(user.isActive() ? "Деактивировать" : "Активировать")
                                    .callbackData(
                                            new InlineButtonCommandDTO(
                                                    InlineButtonCommand.ACTIVE,
                                                    user.getId(),
                                                    messageId
                                            ).toJson()
                                    )
                                    .build()
                            );
                            if (user.getRole() != Role.ADMIN) {
                                row.add(InlineKeyboardButton.builder()
                                        .text(user.getRole() == Role.CAPITAN ?
                                                "Удалить роль капитана" :
                                                "Добавить роль капитана")
                                        .callbackData(
                                                new InlineButtonCommandDTO(
                                                        InlineButtonCommand.CAPITAN_ROLE,
                                                        user.getId(),
                                                        messageId
                                                ).toJson()
                                        )
                                        .build()
                                );
                            }
                        }

                        row.add(InlineKeyboardButton.builder()
                                .text("Удалить пользователя")
                                .callbackData(
                                        new InlineButtonCommandDTO(
                                                InlineButtonCommand.DELETE,
                                                user.getId(),
                                                messageId
                                        ).toJson()
                                )
                                .build()
                        );

                        TgMessagesBotUtils.sendMessage(
                                bot,
                                EditMessageText.builder()
                                        .chatId(chatId)
                                        .text("Выберите комманду")
                                        .messageId(messageId)
                                        .build()
                        );

                        TgMessagesBotUtils.sendMessage(
                                bot,
                                EditMessageReplyMarkup.builder()
                                        .chatId(chatId)
                                        .messageId(messageId)
                                        .replyMarkup(InlineKeyboardMarkup.builder().keyboardRow(row).build())
                                        .build()
                        );
                    }
                });
            }
        });
    }
}
