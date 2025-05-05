package ru.rogotovskiy.auth.dto;

public record JwtResponse(
        String message,
        String token
) {}
