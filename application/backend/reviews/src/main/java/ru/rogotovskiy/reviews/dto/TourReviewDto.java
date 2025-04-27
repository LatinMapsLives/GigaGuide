package ru.rogotovskiy.reviews.dto;

import java.time.LocalDateTime;

public record TourReviewDto(
        Integer id,
        Integer rating,
        String comment,
        LocalDateTime createdAt
) {
}
