package ru.tdd.tgbot.service.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tdd.tgbot.service.commands.inlinebuttons.ActiveCommand;
import ru.tdd.tgbot.service.commands.inlinebuttons.CapitanRoleCommand;
import ru.tdd.tgbot.service.commands.inlinebuttons.DeleteCommand;
import ru.tdd.tgbot.service.commands.inlinebuttons.UserCommand;
import ru.tdd.tgbot.service.commands.with.answers.AddUserUsernameCommand;

/**
 * @author Tribushko Danil
 * @since 21.01.2025
 * Реестр комманд бота
 */
@Component
public class BotRegister {
    private final StartCommand startCommand;
    private final AddUserCommand addUserCommand;
    private final UsersCommand usersCommand;

    private final AddUserUsernameCommand addUserUsernameCommand;

    private final UserCommand userCommand;
    private final ActiveCommand activeCommand;
    private final CapitanRoleCommand capitanRoleCommand;
    private final DeleteCommand deleteCommand;

    @Autowired
    public BotRegister(
            StartCommand startCommand,
            AddUserCommand addUserCommand,
            UsersCommand usersCommand,
            AddUserUsernameCommand addUserUsernameCommand,
            UserCommand userCommand,
            ActiveCommand activeCommand,
            CapitanRoleCommand capitanRoleCommand,
            DeleteCommand deleteCommand
    ) {
        this.startCommand = startCommand;
        this.addUserCommand = addUserCommand;
        this.usersCommand = usersCommand;
        this.addUserUsernameCommand = addUserUsernameCommand;
        this.userCommand = userCommand;
        this.activeCommand = activeCommand;
        this.capitanRoleCommand = capitanRoleCommand;
        this.deleteCommand = deleteCommand;
    }

    public StartCommand getStartCommand() {
        return startCommand;
    }

    public AddUserCommand getAddUserCommand() {
        return addUserCommand;
    }

    public AddUserUsernameCommand getAddUserUsernameCommand() {
        return addUserUsernameCommand;
    }

    public UsersCommand getUsersCommand() {
        return usersCommand;
    }

    public UserCommand getUserCommand() {
        return userCommand;
    }

    public ActiveCommand getActiveCommand() {
        return activeCommand;
    }

    public CapitanRoleCommand getCapitanRoleCommand() {
        return capitanRoleCommand;
    }

    public DeleteCommand getDeleteCommand() {
        return deleteCommand;
    }
}
