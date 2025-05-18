package ru.rogotovskiy.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Обновление данных пользователя")
public record UpdateUserDto(
        @Schema(description = "Новый адрес электронной почты пользователя", example = "newUser@gmail.com")
        String email,
        @Schema(description = "Новое имя пользователя", example = "NewName")
        String username,
        @Schema(description = "Старый пароль", example = "Qwerty123")
        String oldPassword,
        @Schema(description = "Новый пароль", example = "Qwerty1234")
        String newPassword
) {
}
