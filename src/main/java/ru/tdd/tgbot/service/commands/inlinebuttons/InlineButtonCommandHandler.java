package ru.tdd.tgbot.service.commands.inlinebuttons;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.tdd.tgbot.models.dto.InlineButtonCommandDTO;

/**
 * @author Tribushko Danil
 * @since 25.01.2024
 * Обработчик комманд вызываемых при помощи InlineKeyboardButton
 */
public interface InlineButtonCommandHandler {
    void action(TelegramLongPollingBot bot, CallbackQuery callbackQuery, InlineButtonCommandDTO commandDTO);
}
