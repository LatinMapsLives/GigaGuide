package ru.rogotovskiy.toursight.dto.read;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Данные достопримечательности")
public record SightDto(
        @Schema(description = "ID достопримечательности", example = "145")
        Integer id,
        @Schema(description = "Название достопримечательности", example = "Парк Орлёнок")
        String name,
        @Schema(description = "Описание достопримечательности", example = "Парк \"Орлёнок\" — это не просто зона отдыха, а настоящая живая летопись Воронежа...")
        String description,
        @Schema(description = "Город, в котором находится эта достопримечательность", example = "Воронеж")
        String city,
        @Schema(description = "Имя файла картинки", example = "a0d3c84b-f1d1-40e0-81ba-fabffd0d5ed4.jpg")
        String imagePath
) {
}
