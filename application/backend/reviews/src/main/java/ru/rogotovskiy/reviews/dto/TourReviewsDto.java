package ru.rogotovskiy.reviews.dto;

import java.util.List;

public record TourReviewsDto(
        TourReviewDto userReview,
        List<TourReviewDto> otherReviews
) {
}
