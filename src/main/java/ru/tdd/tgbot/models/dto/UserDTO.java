package ru.tdd.tgbot.models.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.tdd.tgbot.models.entities.User;
import ru.tdd.tgbot.models.enums.Role;

/**
 * @author Tribushko Danil
 * @since 24.01.2025
 * Dto пользователя
 */
public class UserDTO {
    private Long id;
    private String username;
    private Long tgId;
    private Role role;
    private boolean isActive;
    private boolean isQueue;
    private Integer order;

    private UserDTO() {
    }

    public static class Builder {
        private Long id;
        private String username;
        private Long tgId;
        private Role role;
        private boolean isActive;
        private boolean isQueue;
        private Integer order;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder tgId(Long tgId) {
            this.tgId = tgId;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Builder isQueue(boolean isQueue) {
            this.isQueue = isQueue;
            return this;
        }

        public Builder order(Integer order) {
            this.order = order;
            return this;
        }

        public UserDTO build() {
            UserDTO userDTO = new UserDTO();

            userDTO.id = this.id;
            userDTO.username = this.username;
            userDTO.tgId = this.tgId;
            userDTO.role = this.role;
            userDTO.isActive = this.isActive;
            userDTO.isQueue = this.isQueue;
            userDTO.order = this.order;

            return userDTO;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTgId() {
        return tgId;
    }

    public void setTgId(Long tgId) {
        this.tgId = tgId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isQueue() {
        return isQueue;
    }

    public void setQueue(boolean queue) {
        isQueue = queue;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static UserDTO fromJson(String json) {
        try {
            return new ObjectMapper().readValue(json, UserDTO.class);
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

    public static UserDTO fromUser(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .tgId(user.getTgId())
                .role(user.getRole())
                .isActive(user.isActive())
                .isQueue(user.isQueue())
                .order(user.getOrder())
                .build();
    }

    public User toUser() {
        return new User()
                .builder()
                .tgId(tgId)
                .username(username)
                .role(role)
                .isActive(isActive)
                .isQueue(isQueue)
                .order(order)
                .build();
    }
}
