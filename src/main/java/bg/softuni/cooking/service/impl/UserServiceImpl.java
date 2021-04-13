package bg.softuni.cooking.service.impl;

import bg.softuni.cooking.model.entity.User;
import bg.softuni.cooking.model.entity.UserRole;
import bg.softuni.cooking.model.entity.UserRoleEntity;
import bg.softuni.cooking.model.service.RoleServiceModel;
import bg.softuni.cooking.model.service.UserServiceModel;
import bg.softuni.cooking.repository.UserRepository;
import bg.softuni.cooking.repository.UserRoleRepository;
import bg.softuni.cooking.service.RoleService;
import bg.softuni.cooking.service.UserRoleService;
import bg.softuni.cooking.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private  final CookingDBUserService cookingDBUserService;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, UserRoleService userRoleService, CookingDBUserService cookingDBUserService, RoleService roleService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
        this.cookingDBUserService = cookingDBUserService;
        this.roleService = roleService;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {

        User user = modelMapper.map(userServiceModel, User.class);

        user.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));

        UserRoleEntity userRoleEntity = userRoleService.findByUserRole(UserRole.USER);

        user.addRole(userRoleEntity);

        userRepository.save(user);

        UserDetails principal = cookingDBUserService.loadUserByUsername(user.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                user.getPassword(),
                principal.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    @Override
    public UserServiceModel findByUsername(String username) {
        return userRepository.findByUsername(username).map(user ->
                modelMapper.map(user, UserServiceModel.class)).orElse(null);
    }

    @Override
    public void seedUsers() {

        if (userRepository.count() == 0){
            UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRole.ADMIN);
            UserRoleEntity userRole = new UserRoleEntity().setRole(UserRole.USER);
            userRoleRepository.saveAll(List.of(adminRole, userRole));

            User admin = new User();
            admin.setFirstName("Tatyana");
            admin.setLastName("Velkova");
            admin.setEmail("test@mail.bg");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("topSecret"));
            admin.setRoles(List.of(adminRole, userRole));


            User user = new User();
            user.setFirstName("Ivan");
            user.setLastName("Diitrov");
            user.setEmail("ivan@mail.bg");
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("topSecret"));
            user.setRoles(List.of(userRole));

            userRepository.saveAll(List.of(admin, user));

        }



    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public List<String> findAllUsernames() {
        return userRepository.findAll().stream().map(User::getUsername).collect(Collectors.toList());
    }

    @Override
    public void changeUserRole(String username, String roleName) {
        User user = userRepository.findByUsername(username).orElse(null);
        //RoleServiceModel roleServiceModel = roleService.findByName(roleName);
        //UserRoleEntity userRoleEntity = modelMapper.map(roleServiceModel, UserRoleEntity.class);
        RoleServiceModel roleServiceModelUser = roleService.findByName("USER");
        UserRoleEntity roleEntityUser = modelMapper.map(roleServiceModelUser, UserRoleEntity.class);
        roleEntityUser.setRole(UserRole.USER);

        RoleServiceModel roleServiceModelAdmin = roleService.findByName("ADMIN");
        UserRoleEntity roleEntityAdmin = modelMapper.map(roleService.findByName("ADMIN"), UserRoleEntity.class);
        roleEntityAdmin.setRole(UserRole.ADMIN);
        List<String> roles = user.getRoles().stream().map(userRoleEntity -> userRoleEntity.getRole().name()).collect(Collectors.toList());

        if (roleName.equals("admin")){
            if (!roles.contains("ADMIN")){
                user.addRole(roleEntityAdmin);
                userRepository.saveAndFlush(user);
            }
        } else if (roleName.equals("user")){
            if (roles.contains("ADMIN")){
                user.removeRole(roleEntityAdmin);
                userRepository.saveAndFlush(user);
            }
        }

    }
}
