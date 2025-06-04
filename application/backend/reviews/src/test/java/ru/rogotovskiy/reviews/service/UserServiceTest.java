package ru.rogotovskiy.reviews.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.rogotovskiy.reviews.entity.User;
import ru.rogotovskiy.reviews.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getUserById_ShouldReturnUser_WhenExists() {
        // Arrange
        User expectedUser = new User(1, "testuser");
        when(userRepository.findById(1)).thenReturn(Optional.of(expectedUser));

        // Act
        User result = userService.getUserById(1);

        // Assert
        assertEquals(expectedUser, result);
    }

    @Test
    void getUserById_ShouldThrow_WhenNotExists() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () ->
                userService.getUserById(1));
    }

    @Test
    void getUsername_ShouldReturnUsername() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(new User(1, "testuser")));

        // Act
        String result = userService.getUsername(1);

        // Assert
        assertEquals("testuser", result);
    }
}