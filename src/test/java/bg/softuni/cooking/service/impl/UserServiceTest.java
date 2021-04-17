package bg.softuni.cooking.service.impl;

import bg.softuni.cooking.model.entity.User;
import bg.softuni.cooking.model.entity.UserRole;
import bg.softuni.cooking.model.entity.UserRoleEntity;
import bg.softuni.cooking.repository.UserRepository;
import bg.softuni.cooking.service.UserService;
import bg.softuni.cooking.service.impl.CookingDBUserService;
import bg.softuni.cooking.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private CookingDBUserService userServiceToTest;

    @Mock
    UserRepository mockUserRepository;

    @BeforeEach
    public void setUp(){
        userServiceToTest = new CookingDBUserService(mockUserRepository);
    }

    @Test
    void testUserNotFound(){
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userServiceToTest.loadUserByUsername("user_does_not_exist");
        });
    }


    @Test
    void testExistingUser(){
        User user = new User();
        user.setUsername("plamen");
        user.setPassword("123");
        UserRoleEntity roleAdmin = new UserRoleEntity();
        roleAdmin.setRole(UserRole.ADMIN);
        UserRoleEntity roleUser = new UserRoleEntity();
        roleUser.setRole(UserRole.USER);
        user.setRoles(List.of(roleUser, roleAdmin));

        Mockito.when(mockUserRepository.findByUsername("plamen"))
                .thenReturn(Optional.of(user));


        UserDetails userDetails = userServiceToTest.loadUserByUsername("plamen");

        Assertions.assertEquals(user.getUsername(), userDetails.getUsername());
        Assertions.assertEquals(2, userDetails.getAuthorities().size());

        List<String> authorities = userDetails.getAuthorities().stream()
                .map(a -> a.getAuthority()).collect(Collectors.toList());

        Assertions.assertTrue(authorities.contains("ROLE_ADMIN"));
        Assertions.assertTrue(authorities.contains("ROLE_USER"));
    }
}
