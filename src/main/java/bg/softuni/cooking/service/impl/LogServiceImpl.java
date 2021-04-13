package bg.softuni.cooking.service.impl;

import bg.softuni.cooking.model.entity.LogEntity;
import bg.softuni.cooking.model.entity.Recipe;
import bg.softuni.cooking.model.entity.User;
import bg.softuni.cooking.model.service.LogServiceModel;
import bg.softuni.cooking.model.service.RecipeServiceModel;
import bg.softuni.cooking.model.service.UserServiceModel;
import bg.softuni.cooking.repository.LogRepository;
import bg.softuni.cooking.service.LogService;
import bg.softuni.cooking.service.RecipeService;
import bg.softuni.cooking.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final RecipeService recipeService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public LogServiceImpl(LogRepository logRepository, RecipeService recipeService, UserService userService, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.recipeService = recipeService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createLog(String action, Long recipeId) {
        RecipeServiceModel recipeServiceModel = recipeService.findById(recipeId);
        Recipe recipe = modelMapper.map(recipeServiceModel, Recipe.class);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserServiceModel userServiceModel = userService.findByUsername(username);
        User user = modelMapper.map(userServiceModel, User.class);

        LogEntity logEntity = new LogEntity();

        logEntity.setUser(user);
        logEntity.setRecipe(recipe);
        logEntity.setAction(action);
        logEntity.setLocalDateTime(LocalDateTime.now());

        logRepository.save(logEntity);
    }

    @Override
    public List<LogServiceModel> findAllLogs() {
        return logRepository.findAll().stream().map(log -> {
            LogServiceModel logServiceModel = modelMapper.map(log, LogServiceModel.class);
            logServiceModel.setUser(log.getUser().getUsername());
            logServiceModel.setRecipe(log.getRecipe().getName());

            return logServiceModel;
        }).collect(Collectors.toList());
    }
}
