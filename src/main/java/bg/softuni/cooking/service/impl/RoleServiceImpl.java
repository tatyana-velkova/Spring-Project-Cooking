package bg.softuni.cooking.service.impl;


import bg.softuni.cooking.model.entity.UserRole;
import bg.softuni.cooking.model.entity.UserRoleEntity;
import bg.softuni.cooking.model.service.RoleServiceModel;
import bg.softuni.cooking.model.service.UserServiceModel;
import bg.softuni.cooking.repository.UserRoleRepository;
import bg.softuni.cooking.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(UserRoleRepository userRoleRepository, ModelMapper modelMapper) {
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RoleServiceModel findByName(String name) {
        UserRole userRole = null;
        if (name.toUpperCase().equals("ADMIN")){
            userRole = UserRole.ADMIN;
        } else if (name.toUpperCase().equals("USER")){
            userRole = UserRole.USER;
        }

        UserRoleEntity userRoleEntity = userRoleRepository.findByRole(userRole).orElse(null);
        RoleServiceModel roleServiceModel = modelMapper.map(userRoleEntity, RoleServiceModel.class);
        roleServiceModel.setName(name);
            return roleServiceModel;
    }
}
