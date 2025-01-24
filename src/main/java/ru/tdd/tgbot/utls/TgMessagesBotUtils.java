package ru.tdd.tgbot.utls;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author Tribushko Danil
 * @since 24.01.2025
 * Утилита для отправки сообщений ботом
 */
public class TgMessagesBotUtils {
    private TgMessagesBotUtils() {
    }

    public static Integer sendMessage(TelegramLongPollingBot bot, SendMessage method) {
        try {
            return bot.execute(method).getMessageId();
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendMessage(TelegramLongPollingBot bot, BotApiMethod<?> method) {
        try {
            bot.execute(method);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
