package ru.rogotovskiy.reviews.dto;

import java.util.List;

public record SightReviewsDto(
        SightReviewDto userReview,
        List<SightReviewDto> otherReviews
) {
}
