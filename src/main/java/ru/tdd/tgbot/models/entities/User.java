package ru.tdd.tgbot.models.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import ru.tdd.tgbot.models.enums.Role;

/**
 * @author Tribushko Danil
 * @since 21.01.2025
 * Сущность пользователя
 */
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(nullable = false, unique = true, name = "username")
    private String username;

    @Column(unique = true, name = "tg_id")
    private Long tgId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role")
    private Role role;

    @Column(name = "fullname")
    private String fullName;

    @ColumnDefault("false")
    @Column(name = "is_active", nullable = false)
    private boolean isActive = false;

    @ColumnDefault("false")
    private boolean isQueue = false;

    @ColumnDefault("-1")
    @Column(name = "\"order\"", nullable = false)
    private Integer order = -1;

    public Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String username;
        private Long tgId;
        private Role role;
        private boolean isActive = false;
        private boolean isQueue = false;
        private Integer order = -1;
        private String fullName;

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

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public User build() {
            User user = new User();

            user.setUsername(this.username);
            user.setTgId(this.tgId);
            user.setRole(this.role);
            user.setActive(this.isActive);
            user.setQueue(this.isQueue);
            user.setOrder(this.order);
            user.setFullName(this.fullName);

            return user;
        }
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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public boolean isQueue() {
        return isQueue;
    }

    public void setQueue(boolean queue) {
        isQueue = queue;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
