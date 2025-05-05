package ru.rogotovskiy.auth.dto;

public record RegistrationUserDTO(
        String username,
        String email,
        String password,
        String confirmPassword
) {
}
