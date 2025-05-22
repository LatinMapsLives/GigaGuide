package ru.rogotovskiy.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Данные для регистрации пользователя")
public record RegistrationUserDTO(
        @Schema(description = "Имя пользователя", example = "user")
        String username,
        @Schema(description = "Адрес электронной почты пользователя", example = "user@gmail.com")
        String email,
        @Schema(description = "Пароль пользователя", example = "Qwerty123")
        String password,
        @Schema(description = "Подтверждение пароля", example = "Qwerty123")
        String confirmPassword
) {
}
