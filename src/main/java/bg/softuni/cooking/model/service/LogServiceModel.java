package bg.softuni.cooking.model.service;

import bg.softuni.cooking.model.entity.Recipe;
import bg.softuni.cooking.model.entity.User;

import java.time.LocalDateTime;

public class LogServiceModel {
    private Long id;
    private String user;
    private String recipe;
    private String action;
    private LocalDateTime localDateTime;

    public LogServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
