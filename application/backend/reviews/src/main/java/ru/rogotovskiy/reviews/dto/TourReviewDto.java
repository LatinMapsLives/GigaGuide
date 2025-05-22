package ru.rogotovskiy.reviews.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Отзыв о туре")
public record TourReviewDto(
        @Schema(description = "ID тура", example = "1")
        Integer id,
        @Schema(description = "Оценка тура", example = "5")
        Integer rating,
        @Schema(description = "Комментарий в отзыве о туре", example = "Отличный тур...")
        String comment,
        @Schema(description = "Дата и время создания отзыва", example = "2025-05-05T12:30:00")
        LocalDateTime createdAt
) {
}
