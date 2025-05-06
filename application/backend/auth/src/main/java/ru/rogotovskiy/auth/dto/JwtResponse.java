package ru.rogotovskiy.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ после успешной авторизации")
public record JwtResponse(
        @Schema(description = "Сообщение от сервера", example = "Вы успешно авторизовались")
        String message,
        @Schema(description = "JWT токен", example = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6Wy....")
        String token
) {}
