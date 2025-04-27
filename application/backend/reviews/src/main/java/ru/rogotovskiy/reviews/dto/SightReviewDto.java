package ru.rogotovskiy.reviews.dto;

import java.time.LocalDateTime;

public record SightReviewDto(
        Integer id,
        Integer rating,
        String comment,
        LocalDateTime createdAt
) {
}
