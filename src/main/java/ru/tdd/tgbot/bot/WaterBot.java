package ru.tdd.tgbot.bot;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.tdd.tgbot.models.dto.InlineButtonCommandDTO;
import ru.tdd.tgbot.models.enums.BotCommand;
import ru.tdd.tgbot.repositories.CommandEntityRepository;
import ru.tdd.tgbot.service.commands.BotRegister;

import java.util.Optional;

/**
 * @author Tribushko Danil
 * @since 21.01.2025
 * Команда телеграмм бота, которая является ответом на определенную команду
 */
@Component
@PropertySource("classpath:secret.properties")
public class WaterBot extends TelegramLongPollingBot {
    @Value("${telegram.bot.name}")
    private String name;
    private final CommandEntityRepository commandEntityRepository;

    private final BotRegister register;

    @Autowired
    public WaterBot(@Value("${telegram.bot.token}") String token, BotRegister register, CommandEntityRepository commandEntityRepository) {
        super(token);
        this.register = register;
        this.commandEntityRepository = commandEntityRepository;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            Optional<BotCommand> commandOpt = BotCommand.getByTextOpt(message.getText());
            if (commandOpt.isPresent()) {
                switch (commandOpt.get()) {
                    case ADD_USER -> register.getAddUserCommand().action(this, update);
                    case START -> register.getStartCommand().action(this, update);
                    case USERS -> register.getUsersCommand().action(this, update);
                    case WATER -> register.getWaterCommand().action(this, update);
                    case INIT_WATER -> register.getInitWaterCommand().action(this, update);
                }
            } else {
                commandEntityRepository.findFirstByUser_TgIdOrderByIdDesc(chatId).ifPresent(commandEntity -> {
                    switch (commandEntity.getCommand()) {
                        case ADD_USER_USERNAME ->
                                register.getAddUserUsernameCommand().action(this, update, commandEntity);
                    }
                });
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            InlineButtonCommandDTO command = InlineButtonCommandDTO.fromJson(data);
            switch (command.getCommand()) {
                case USER -> register.getUserCommand().action(this, callbackQuery, command);
                case ACTIVE -> register.getActiveCommand().action(this, callbackQuery, command);
                case CAPITAN_ROLE -> register.getCapitanRoleCommand().action(this, callbackQuery, command);
                case DELETE -> register.getDeleteCommand().action(this, callbackQuery, command);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @PostConstruct
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
    }
}
