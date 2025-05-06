package ru.rogotovskiy.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Данные пользователя")
public record UserDto(
        @Schema(description = "Имя пользователя", example = "user")
        String username,
        @Schema(description = "Адрес электронной почты пользователя", example = "user@gmail.com")
        String email
) {
}
