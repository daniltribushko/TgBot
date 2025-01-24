package ru.tdd.tgbot.service.commands.with.answers;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tdd.tgbot.models.entities.CommandEntity;

/**
 * @author Tribushko Danil
 * @since 24.01.2025
 * Команда бота, которая принимает ответ на последнюю команду
 */
public interface BotCommandWithAnswerHandler {
    void action(TelegramLongPollingBot bot, Update update, CommandEntity command);
}
