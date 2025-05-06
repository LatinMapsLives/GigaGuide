package ru.rogotovskiy.toursight.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ сервера при ошибке")
public record ErrorResponse(
        @Schema(description = "Сообщение от серва об ошибке", example = "Момент с id = 53 не найден")
        String message
) {
}
