package ru.tdd.tgbot.models.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Tribushko Danil
 * @since 21.01.2025
 * Комманда бота телеграмм
 */
public enum BotCommand {
    START(
            "/start",
            "Начать общение с ботом или обновить клавиатуру",
            List.of(Role.ADMIN, Role.CAPITAN, Role.USER)
    ),
    ADD_USER(
            "/add_user",
            "Добавить пользователя",
            List.of(Role.ADMIN, Role.CAPITAN)
    ),
    USERS(
            "/users",
            "Получить список пользователей",
            List.of(Role.ADMIN, Role.CAPITAN)
    ),
    WATER(
            "/water",
            "Получить список водоносов",
            List.of(Role.ADMIN, Role.CAPITAN, Role.USER)
    ),
    INIT_WATER(
            "/init_water",
            "Инициализировать очередь водоносов",
            List.of(Role.ADMIN, Role.CAPITAN)
    );

    private final String text;
    private final String description;
    private final List<Role> roles;

    BotCommand(String text, String description, List<Role> roles) {
        this.text = text;
        this.description = description;
        this.roles = roles;
    }

    public String getText() {
        return text;
    }

    public String getDescription() {
        return description;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public static Optional<BotCommand> getByTextOpt(String text) {
        return Arrays.stream(values()).filter(e -> e.text.equals(text)).findFirst();
    }
}
