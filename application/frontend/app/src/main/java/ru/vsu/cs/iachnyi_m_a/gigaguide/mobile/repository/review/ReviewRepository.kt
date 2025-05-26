package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.review

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.review.ReviewSet

interface ReviewRepository {

    suspend fun getAllReviews(token: String, objectId: Int): ReviewSet?
    suspend fun deleteReview(token: String, id: Int): Boolean?
    suspend fun addReview(token: String, objectId: Long, rating: Int, comment: String): Boolean?

}