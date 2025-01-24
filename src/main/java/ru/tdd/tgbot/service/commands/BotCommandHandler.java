package ru.tdd.tgbot.service.commands;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author Tribushko Danil
 * @since 21.01.2025
 */
public interface BotCommandHandler {
    void action(TelegramLongPollingBot bot, Update update);
}
