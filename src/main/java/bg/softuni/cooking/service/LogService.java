package bg.softuni.cooking.service;

import bg.softuni.cooking.model.service.LogServiceModel;

import java.util.List;

public interface LogService {
    void createLog(String action, Long recipeId);

    List<LogServiceModel> findAllLogs();
}
