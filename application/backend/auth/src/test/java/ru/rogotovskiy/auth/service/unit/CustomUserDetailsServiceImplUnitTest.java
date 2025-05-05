package ru.rogotovskiy.auth.service.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.rogotovskiy.auth.entity.Role;
import ru.rogotovskiy.auth.entity.User;
import ru.rogotovskiy.auth.repository.UserRepository;
import ru.rogotovskiy.auth.service.CustomUserDetailsService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceImplUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {
        // Дано
        String email = "test@example.com";
        User userEntity = new User();
        userEntity.setEmail(email);
        userEntity.setUsername("testuser");
        userEntity.setPassword("encodedPassword");

        Role role1 = new Role();
        role1.setName("ROLE_USER");
        Role role2 = new Role();
        role2.setName("ROLE_ADMIN");
        userEntity.setRoles(List.of(role1, role2));

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        // Действие
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Проверка
        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertEquals(2, userDetails.getAuthorities().size());
    }

    @Test
    void loadUserByUsername_ShouldThrowUsernameNotFoundException_WhenUserNotFound() {
        // Дано
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Действие + проверка
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername(email)
        );
        assertEquals("Пользователь с почтой nonexistent@example.com не найден", exception.getMessage());
    }

    @Test
    void loadUserByUsername_ShouldReturnUserWithNoAuthorities_WhenUserHasNoRoles() {
        // Дано
        String email = "user@example.com";
        User userEntity = new User();
        userEntity.setEmail(email);
        userEntity.setUsername("simpleuser");
        userEntity.setPassword("password");
        userEntity.setRoles(List.of());

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        // Действие
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Проверка
        assertNotNull(userDetails);
        assertEquals("simpleuser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().isEmpty());
    }
}