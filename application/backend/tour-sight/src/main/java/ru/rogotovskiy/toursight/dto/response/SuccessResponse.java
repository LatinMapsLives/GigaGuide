package ru.rogotovskiy.toursight.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Успешный ответ от сервера")
public record SuccessResponse(
        @Schema(description = "Сообщение об успешно выполнение действия", example = "Момент обновлён успешно")
        String message
) {
}
