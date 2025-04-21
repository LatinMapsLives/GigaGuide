package ru.rogotovskiy.userservice.dto;

public record UpdateUserDto(
        String email,
        String username,
        String oldPassword,
        String newPassword
) {
}
