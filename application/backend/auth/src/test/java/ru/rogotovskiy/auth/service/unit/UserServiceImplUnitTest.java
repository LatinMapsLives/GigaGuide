package ru.rogotovskiy.auth.service.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.rogotovskiy.auth.dto.RegistrationUserDTO;
import ru.rogotovskiy.auth.entity.Role;
import ru.rogotovskiy.auth.entity.User;
import ru.rogotovskiy.auth.repository.UserRepository;
import ru.rogotovskiy.auth.service.RoleService;
import ru.rogotovskiy.auth.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void findByUsername_ShouldReturnUser_WhenUserExists() {
        // Дано
        String username = "testuser";
        User expectedUser = new User();
        expectedUser.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        // Действие
        Optional<User> actualUser = userService.findByUsername(username);

        // Проверка
        assertTrue(actualUser.isPresent());
        assertEquals(username, actualUser.get().getUsername());
        verify(userRepository).findByUsername(username);
    }

    @Test
    void findByUsername_ShouldReturnEmpty_WhenUserNotExists() {
        // Дано
        String username = "nonexistent";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Действие
        Optional<User> actualUser = userService.findByUsername(username);

        // Проверка
        assertTrue(actualUser.isEmpty());
        verify(userRepository).findByUsername(username);
    }

    @Test
    void findByEmail_ShouldReturnUser_WhenUserExists() {
        // Дано
        String email = "test@example.com";
        User expectedUser = new User();
        expectedUser.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(expectedUser));

        // Действие
        Optional<User> actualUser = userService.findByEmail(email);

        // Проверка
        assertTrue(actualUser.isPresent());
        assertEquals(email, actualUser.get().getEmail());
        verify(userRepository).findByEmail(email);
    }

    @Test
    void findByEmail_ShouldReturnEmpty_WhenUserNotExists() {
        // Дано
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Действие
        Optional<User> actualUser = userService.findByEmail(email);

        // Проверка
        assertTrue(actualUser.isEmpty());
        verify(userRepository).findByEmail(email);
    }

    @Test
    void createUser_ShouldSaveUserWithEncodedPasswordAndDefaultRole() {
        // Дано
        RegistrationUserDTO dto = new RegistrationUserDTO(
                "newuser",
                "new@example.com",
                "rawpassword",
                "rawpassword"
        );

        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        when(passwordEncoder.encode("rawpassword")).thenReturn("encodedPassword");
        when(roleService.findByName("ROLE_USER")).thenReturn(userRole);

        // Действие
        userService.createUser(dto);

        // Проверка
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertNull(savedUser.getId()); // ID должен генерироваться БД
        assertEquals("newuser", savedUser.getUsername());
        assertEquals("new@example.com", savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());

        List<Role> roles = savedUser.getRoles();
        assertEquals(1, roles.size());
        assertEquals("ROLE_USER", roles.get(0).getName());

        verify(passwordEncoder).encode("rawpassword");
        verify(roleService).findByName("ROLE_USER");
    }

    @Test
    void createUser_ShouldUsePasswordEncoder() {
        // Дано
        RegistrationUserDTO dto = new RegistrationUserDTO(
                "user", "email@test.com", "password", "password"
        );

        when(passwordEncoder.encode("password")).thenReturn("encoded123");
        when(roleService.findByName("ROLE_USER")).thenReturn(new Role());

        // Действие
        userService.createUser(dto);

        // Проверка
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        assertEquals("encoded123", userCaptor.getValue().getPassword());
    }
}