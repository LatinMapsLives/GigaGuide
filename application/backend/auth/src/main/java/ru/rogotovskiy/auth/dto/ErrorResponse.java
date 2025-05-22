package ru.rogotovskiy.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Ответ при ошибке")
public record ErrorResponse(
        @Schema(description = "Сообщение от сервера", example = "Пользователь с именем user уже существует")
        String message,
        @Schema(description = "Время возникновения ошибки", example = "2025-05-05T21:15:30")
        LocalDateTime timestamp
) {
}
