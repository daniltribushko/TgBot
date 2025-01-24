package ru.tdd.tgbot.models.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Tribushko Danil
 * @since 24.01.2025
 * Команды бота, которые
 */
public enum BotCommandWithAnswer {
    ADD_USER_USERNAME("add_user_username");

    private final String text;

    BotCommandWithAnswer(String text) {
        this.text = text;
    }

    /** Получить команду по её названию */
    public static Optional<BotCommandWithAnswer> getByTextOpt(String text) {
        return Arrays.stream(values()).filter(e -> e.text.equals(text)).findFirst();
    }
}