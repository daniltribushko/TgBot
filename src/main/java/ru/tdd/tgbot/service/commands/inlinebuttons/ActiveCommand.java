package ru.tdd.tgbot.service.commands.inlinebuttons;

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
 * Команда для активации/деактивации пользователя
 */
@Component
public class ActiveCommand implements InlineButtonCommandHandler {
    private final UserRepository userRepository;

    public ActiveCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void action(TelegramLongPollingBot bot, CallbackQuery callbackQuery, InlineButtonCommandDTO commandDTO) {
        MaybeInaccessibleMessage message = callbackQuery.getMessage();
        Long chatId = message.getChatId();

        userRepository.findByTgId(chatId).ifPresent(current -> {
            if (InlineButtonCommand.ACTIVE.getRoles().contains(current.getRole())) {
                userRepository.findById(commandDTO.getUserId()).ifPresent(user -> {
                   user.setActive(!user.isActive());
                   userRepository.save(user);

                   String text = user.isActive() ? "Пользователь активирован" : "Пользователь деактивирован";

                    TgMessagesBotUtils.sendMessage(
                            bot,
                            SendMessage.builder()
                                    .chatId(chatId)
                                    .text(text)
                            .build()
                    );
                });
            }
        });
    }
}
