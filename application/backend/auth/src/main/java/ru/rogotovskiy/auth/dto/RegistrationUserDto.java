package ru.rogotovskiy.auth.dto;

public record RegistrationUserDto(
        String username,
        String email,
        String password,
        String confirmPassword
) {
}
