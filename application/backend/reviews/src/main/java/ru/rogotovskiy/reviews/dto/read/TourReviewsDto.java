package ru.rogotovskiy.reviews.dto.read;

import java.util.List;

public record TourReviewsDto(
        TourReviewDto userReview,
        List<TourReviewDto> otherReviews
) {
}
