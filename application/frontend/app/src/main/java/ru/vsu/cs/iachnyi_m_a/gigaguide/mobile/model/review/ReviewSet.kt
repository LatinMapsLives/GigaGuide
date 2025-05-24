package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.review

data class ReviewSet (
    var myReview: Review?,
    var otherReviews: List<Review>
)