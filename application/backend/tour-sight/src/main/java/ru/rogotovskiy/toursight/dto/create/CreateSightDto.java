package ru.rogotovskiy.toursight.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Данные для создания достопримечательности")
public record CreateSightDto(
        @Schema(description = "Название достопримечательности", example = "Парк Орлёнок")
        String name,
        @Schema(description = "Описание достопримечательности", example = "Парк \"Орлёнок\" — это не просто зона отдыха, а настоящая живая летопись Воронежа...")
        String description,
        @Schema(description = "Город, в котором находится эта достопримечательность", example = "Воронеж")
        String city,
        @Schema(description = "Широта", example = "51.657333")
        BigDecimal latitude,
        @Schema(description = "Долгота", example = "39.216445")
        BigDecimal longitude
) {
}
