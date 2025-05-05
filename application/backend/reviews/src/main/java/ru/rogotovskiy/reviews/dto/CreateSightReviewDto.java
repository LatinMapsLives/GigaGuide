package ru.rogotovskiy.reviews.dto;

public record CreateSightReviewDto(
        Integer rating,
        String comment
) {
}
