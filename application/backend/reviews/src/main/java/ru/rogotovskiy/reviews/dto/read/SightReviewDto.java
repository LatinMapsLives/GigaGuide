package ru.rogotovskiy.reviews.dto.read;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Отзыв о достопримечательности")
public class SightReviewDto {
        @Schema(description = "ID достопримечательности", example = "1")
        Integer id;
        String username;
        @Schema(description = "Оценка достопримечательности", example = "5")
        Integer rating;
        @Schema(description = "Комментарий о достопримечательности", example = "Отличное место...")
        String comment;
        @Schema(description = "Дата и время создания отзыва", example = "2025-05-05T12:30:00")
        LocalDateTime createdAt;
}
