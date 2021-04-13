package bg.softuni.cooking.service.impl;

import bg.softuni.cooking.model.entity.UserRole;
import bg.softuni.cooking.model.entity.UserRoleEntity;
import bg.softuni.cooking.repository.UserRoleRepository;
import bg.softuni.cooking.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserRoleEntity findByUserRole(UserRole userRole) {
        return userRoleRepository.findByRole(userRole).orElseThrow(() -> new IllegalStateException("User role not found. Please seed user roles in DB"));

    }
}
