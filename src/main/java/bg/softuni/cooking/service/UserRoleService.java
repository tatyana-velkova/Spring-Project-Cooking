package bg.softuni.cooking.service;

import bg.softuni.cooking.model.entity.UserRole;
import bg.softuni.cooking.model.entity.UserRoleEntity;

public interface UserRoleService {
    public UserRoleEntity findByUserRole(UserRole userRole);
}
