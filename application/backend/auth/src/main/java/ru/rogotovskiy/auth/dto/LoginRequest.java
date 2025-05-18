package ru.rogotovskiy.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Данные для авторизации пользователя")
public record LoginRequest(
        @Schema(description = "Адрес электронной почты пользователя", example = "user@gmail.com")
        String email,
        @Schema(description = "Пароль пользователя", example = "Qwerty123")
        String password
) {
}
