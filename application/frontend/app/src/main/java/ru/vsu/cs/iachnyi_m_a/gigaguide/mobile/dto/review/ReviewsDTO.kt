package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.review

data class ReviewsDTO(
    var userReview: ReviewDTO?,
    var otherReviews: List<ReviewDTO>
)
