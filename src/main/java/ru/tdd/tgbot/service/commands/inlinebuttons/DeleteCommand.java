package ru.tdd.tgbot.service.commands.inlinebuttons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.MaybeInaccessibleMessage;
import ru.tdd.tgbot.models.dto.InlineButtonCommandDTO;
import ru.tdd.tgbot.models.enums.InlineButtonCommand;
import ru.tdd.tgbot.repositories.UserRepository;
import ru.tdd.tgbot.utls.TgMessagesBotUtils;

/**
 * @author Tribushko Danil
 * @since 25.01.2025
 * Команда для удаления пользователя
 */
@Component
public class DeleteCommand implements InlineButtonCommandHandler {
    private final UserRepository userRepository;

    @Autowired
    public DeleteCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void action(TelegramLongPollingBot bot, CallbackQuery callbackQuery, InlineButtonCommandDTO commandDTO) {
        MaybeInaccessibleMessage message = callbackQuery.getMessage();
        Long chatId = message.getChatId();

        userRepository.findByTgId(chatId).ifPresent(current -> {
            if (InlineButtonCommand.DELETE.getRoles().contains(current.getRole())) {
                userRepository.findById(commandDTO.getUserId()).ifPresent(user -> {
                    userRepository.delete(user);
                    TgMessagesBotUtils.sendMessage(
                            bot,
                            SendMessage.builder()
                                    .text("Пользователь удален")
                                    .chatId(chatId)
                                    .build()
                    );
                });
            }
        });
    }
}
