package ru.rogotovskiy.reviews.dto.read;

import java.util.List;

public record SightReviewsDto(
        SightReviewDto userReview,
        List<SightReviewDto> otherReviews
) {
}
