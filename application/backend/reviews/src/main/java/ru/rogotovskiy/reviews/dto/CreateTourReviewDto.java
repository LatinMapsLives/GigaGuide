package ru.rogotovskiy.reviews.dto;

public record CreateTourReviewDto(
        Integer rating,
        String comment
) {
}
