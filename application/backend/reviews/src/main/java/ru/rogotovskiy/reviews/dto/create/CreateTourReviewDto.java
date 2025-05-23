package ru.rogotovskiy.reviews.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Создание отзыва о туре")
public record CreateTourReviewDto(
        @Schema(description = "Оценка тура", example = "5")
        Integer rating,
        @Schema(description = "Комментарий в отзыве о туре", example = "Отличный тур...")
        String comment
) {
}
