package ru.tdd.tgbot.models.enums;

import java.util.List;

/**
 * @author Tribushko Danil
 * @since 25.01.2025
 * Команды бота вызываемая при помощи InlineKeyboardButton
 */
public enum InlineButtonCommand {
    USER("/user", List.of(Role.ADMIN, Role.CAPITAN)),
    ACTIVE("/active", List.of(Role.ADMIN, Role.CAPITAN)),
    DELETE("/delete", List.of(Role.ADMIN, Role.CAPITAN)),
    CAPITAN_ROLE("/capitan_role", List.of(Role.ADMIN));

    private final String text;
    private final List<Role> roles;

    InlineButtonCommand(String text, List<Role> roles) {
        this.text = text;
        this.roles = roles;
    }

    public String getText() {
        return text;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
