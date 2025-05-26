package ru.rogotovskiy.reviews.dto.read;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "Отзыв о туре")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourReviewDto {
        @Schema(description = "ID тура", example = "1")
        Integer id;
        String username;
        @Schema(description = "Оценка тура", example = "5")
        Integer rating;
        @Schema(description = "Комментарий в отзыве о туре", example = "Отличный тур...")
        String comment;
        @Schema(description = "Дата и время создания отзыва", example = "2025-05-05T12:30:00")
        LocalDateTime createdAt;
}
