package bg.softuni.cooking.service;

import bg.softuni.cooking.model.service.UserServiceModel;

import java.util.List;

public interface UserService {

    void registerUser(UserServiceModel userServiceModel);

    UserServiceModel findByUsername(String username);

    void seedUsers();

    boolean usernameExists(String username);

    List<String> findAllUsernames();

    void changeUserRole(String username, String roleName);
}
