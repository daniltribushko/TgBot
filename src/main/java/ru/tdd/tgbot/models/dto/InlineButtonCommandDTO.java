package ru.tdd.tgbot.models.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.tdd.tgbot.models.enums.InlineButtonCommand;

/**
 * @author Tribushko Danil
 * @since 25.01.2025
 * Dto команды бота вызываемой при помощи InlineKeyboardButton
 */
public class InlineButtonCommandDTO {
    private InlineButtonCommand command;
    private Long userId;
    private Integer messageId;

    public InlineButtonCommandDTO() {}

    public InlineButtonCommandDTO(InlineButtonCommand command, Long userId, Integer messageId) {
        this.command = command;
        this.userId = userId;
        this.messageId = messageId;
    }

    public InlineButtonCommandDTO(InlineButtonCommand command, Long userId) {
        this.command = command;
        this.userId = userId;
    }

    public InlineButtonCommand getCommand() {
        return command;
    }

    public void setCommand(InlineButtonCommand command) {
        this.command = command;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public static InlineButtonCommandDTO fromJson(String json) {
        try {
            return new ObjectMapper().readValue(json, InlineButtonCommandDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
