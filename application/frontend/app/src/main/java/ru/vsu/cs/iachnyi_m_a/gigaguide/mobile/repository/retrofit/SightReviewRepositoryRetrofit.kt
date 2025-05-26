package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit

import retrofit2.Response
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.SightReviewAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper.review.ReviewsDTOMapper
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.review.CreateReviewDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.review.ReviewsDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.review.ReviewSet
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.review.SightReviewRepository

class SightReviewRepositoryRetrofit(private val sightReviewAPI: SightReviewAPI) :
    SightReviewRepository {
    override suspend fun getAllReviews(
        token: String,
        objectId: Int
    ): ReviewSet? {
        var response: Response<ReviewsDTO> =
            sightReviewAPI.getAll("Bearer $token", objectId).execute()
        return if (response.isSuccessful) {
            ReviewsDTOMapper().map(response.body()!!)
        } else {
            null
        }
    }

    override suspend fun deleteReview(token: String, id: Int): Boolean? {
        var response: Response<String> = sightReviewAPI.deleteReview("Bearer $token", id).execute()
        return response.isSuccessful
    }

    override suspend fun addReview(
        token: String,
        objectId: Long,
        rating: Int,
        comment: String
    ): Boolean? {
        var response: Response<String> = sightReviewAPI.addReview(
            token = "Bearer $token",
            sightId = objectId.toInt(),
            createReviewDTO = CreateReviewDTO(rating = rating, comment = comment)
        ).execute()
        return response.isSuccessful
    }
}