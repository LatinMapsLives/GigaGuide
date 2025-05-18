package ru.rogotovskiy.toursight.dto.read;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Данные момента")
public record MomentDto(
        @Schema(description = "ID момента", example = "134")
        Integer id,
        @Schema(description = "Название момента", example = "Мемориальный некрополь")
        String name,
        @Schema(description = "Порядковый номер момента", example = "1")
        Integer orderNumber,
        @Schema(description = "Имя файла картинки", example = "a0d3c84b-f1d1-40e0-81ba-fabffd0d5ed4.jpg")
        String imagePath
) {
}
