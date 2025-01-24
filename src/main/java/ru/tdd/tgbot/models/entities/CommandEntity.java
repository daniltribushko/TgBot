package ru.tdd.tgbot.models.entities;

import jakarta.persistence.*;
import ru.tdd.tgbot.models.enums.BotCommandWithAnswer;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 24.01.2025
 * Сущность комманды бота
 */
@Entity
@Table(name = "commands")
public class CommandEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "bot_command", nullable = false)
    private BotCommandWithAnswer command;

    @Column(name = "body")
    private String body;

    @Column(name = "bot_message_id", nullable = false)
    private Integer botMessageId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    public CommandEntity() {
    }

    public Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private BotCommandWithAnswer command;
        private String body;
        private Integer botMessageId;
        private User user;

        public Builder() {
        }

        public Builder command(BotCommandWithAnswer command) {
            this.command = command;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder botMessageId(Integer botMessageId) {
            this.botMessageId = botMessageId;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public CommandEntity build() {
            CommandEntity commandEntity = new CommandEntity();
            commandEntity.command = this.command;
            commandEntity.body = this.body;
            commandEntity.botMessageId = this.botMessageId;
            commandEntity.user = this.user;
            commandEntity.created = LocalDateTime.now();

            return commandEntity;
        }
    }

    public BotCommandWithAnswer getCommand() {
        return command;
    }

    public void setCommand(BotCommandWithAnswer command) {
        this.command = command;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Integer getBotMessageId() {
        return botMessageId;
    }

    public void setBotMessageId(Integer botMessageId) {
        this.botMessageId = botMessageId;
    }
}
