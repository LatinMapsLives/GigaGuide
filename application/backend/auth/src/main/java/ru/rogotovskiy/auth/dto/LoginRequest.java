package ru.rogotovskiy.auth.dto;

public record LoginRequest(
        String email,
        String password
) {
}
