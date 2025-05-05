package ru.rogotovskiy.auth.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        LocalDateTime timestamp
) {
}
