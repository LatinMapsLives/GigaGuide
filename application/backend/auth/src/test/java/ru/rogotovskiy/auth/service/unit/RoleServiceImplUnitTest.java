package ru.rogotovskiy.auth.service.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.rogotovskiy.auth.entity.Role;
import ru.rogotovskiy.auth.repository.RoleRepository;
import ru.rogotovskiy.auth.service.RoleService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplUnitTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    void findByName_ShouldReturnRole_WhenRoleExists() {
        // Дано
        String roleName = "ROLE_ADMIN";
        Role expectedRole = new Role();
        expectedRole.setName(roleName);

        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(expectedRole));

        // Действие
        Role actualRole = roleService.findByName(roleName);

        // Проверка
        assertNotNull(actualRole);
        assertEquals(roleName, actualRole.getName());
        verify(roleRepository).findByName(roleName);
    }

    @Test
    void findByName_ShouldThrowException_WhenRoleNotFound() {
        // Дано
        String nonExistentRole = "ROLE_UNKNOWN";
        when(roleRepository.findByName(nonExistentRole)).thenReturn(Optional.empty());

        // Действие & Проверка
        assertThrows(
                RuntimeException.class,
                () -> roleService.findByName(nonExistentRole)
        );
        verify(roleRepository).findByName(nonExistentRole);
    }

    @Test
    void findByName_ShouldBeCaseSensitive() {
        // Дано
        String roleName = "ROLE_USER";
        String differentCaseName = "role_user";
        Role expectedRole = new Role();
        expectedRole.setName(roleName);

        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(expectedRole));
        when(roleRepository.findByName(differentCaseName)).thenReturn(Optional.empty());

        // Действие & Проверка
        assertDoesNotThrow(() -> roleService.findByName(roleName));
        assertThrows(
                RuntimeException.class,
                () -> roleService.findByName(differentCaseName)
        );

        verify(roleRepository).findByName(roleName);
        verify(roleRepository).findByName(differentCaseName);
    }
}