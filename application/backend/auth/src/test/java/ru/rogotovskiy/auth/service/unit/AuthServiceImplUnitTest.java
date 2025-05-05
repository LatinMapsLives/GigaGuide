package ru.rogotovskiy.auth.service.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import ru.rogotovskiy.auth.dto.LoginRequest;
import ru.rogotovskiy.auth.dto.RegistrationUserDTO;
import ru.rogotovskiy.auth.entity.User;
import ru.rogotovskiy.auth.exception.PasswordMismatchException;
import ru.rogotovskiy.auth.exception.UserAlreadyExistsException;
import ru.rogotovskiy.auth.exception.UserNotFoundException;
import ru.rogotovskiy.auth.service.AuthService;
import ru.rogotovskiy.auth.service.JwtService;
import ru.rogotovskiy.auth.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplUnitTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_ShouldSuccessfullyRegisterUser_WhenAllConditionsMet() {
        // Дано
        RegistrationUserDTO registrationUserDTO = new RegistrationUserDTO(
                "testuser", "test@example.com", "password", "password"
        );

        when(userService.findByUsername("testuser")).thenReturn(Optional.empty());
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.empty());
        doNothing().when(userService).createUser(registrationUserDTO);

        // Проверка + действие
        assertDoesNotThrow(() -> authService.register(registrationUserDTO));
        verify(userService).createUser(registrationUserDTO);
    }

    @Test
    void register_ShouldThrowUserAlreadyExistsException_WhenUsernameExists() {
        // Дано
        RegistrationUserDTO registrationUserDTO = new RegistrationUserDTO(
                "existinguser", "test@example.com", "password", "password"
        );

        when(userService.findByUsername("existinguser")).thenReturn(Optional.of(new User()));

        // Проверка + действие
        UserAlreadyExistsException exception = assertThrows(
                UserAlreadyExistsException.class,
                () -> authService.register(registrationUserDTO)
        );
        assertEquals("Пользователь с именем existinguser уже существует", exception.getMessage());
        verify(userService, never()).createUser(any());
    }

    @Test
    void register_ShouldThrowUserAlreadyExistsException_WhenEmailExists() {
        // Дано
        RegistrationUserDTO registrationUserDTO = new RegistrationUserDTO(
                "newuser", "existing@example.com", "password", "password"
        );

        when(userService.findByUsername("newuser")).thenReturn(Optional.empty());
        when(userService.findByEmail("existing@example.com")).thenReturn(Optional.of(new User()));

        // Проверка + действие
        UserAlreadyExistsException exception = assertThrows(
                UserAlreadyExistsException.class,
                () -> authService.register(registrationUserDTO)
        );
        assertEquals("Пользователь с почтой existing@example.com уже существует", exception.getMessage());
        verify(userService, never()).createUser(any());
    }

    @Test
    void register_ShouldThrowPasswordMismatchException_WhenPasswordsDontMatch() {
        // Дано
        RegistrationUserDTO registrationUserDTO = new RegistrationUserDTO(
                "newuser", "test@example.com", "password", "differentpassword"
        );

        when(userService.findByUsername("newuser")).thenReturn(Optional.empty());
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.empty());

        // Проверка + действие
        PasswordMismatchException exception = assertThrows(
                PasswordMismatchException.class,
                () -> authService.register(registrationUserDTO)
        );
        assertEquals("Пароли не совпадают", exception.getMessage());
        verify(userService, never()).createUser(any());
    }

    @Test
    void authorize_ShouldReturnToken_WhenCredentialsAreValid() {
        // Дано
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password");
        User user = new User();
        String expectedToken = "generated.jwt.token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(expectedToken);

        // Действие
        String actualToken = authService.authorize(loginRequest);

        // Проверка
        assertEquals(expectedToken, actualToken);
        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken("test@example.com", "password")
        );
        verify(jwtService).generateToken(user);
    }

    @Test
    void authorize_ShouldThrowUserNotFoundException_WhenUserNotFound() {
        // Дано
        LoginRequest loginRequest = new LoginRequest("nonexistent@example.com", "password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));
        when(userService.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Проверка + Действие
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> authService.authorize(loginRequest)
        );
        assertEquals("Пользователь с почтой nonexistent@example.com не найден", exception.getMessage());
        verify(jwtService, never()).generateToken(any());
    }
}