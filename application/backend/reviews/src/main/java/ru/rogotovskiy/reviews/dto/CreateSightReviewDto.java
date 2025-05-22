package ru.rogotovskiy.reviews.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Создание отзыва о достопримечательности")
public record CreateSightReviewDto(
        @Schema(description = "Оценка достопримечательности", example = "5")
        Integer rating,
        @Schema(description = "Комментарий в отзыве о достопримечательности", example = "Отличное место...")
        String comment
) {
}
