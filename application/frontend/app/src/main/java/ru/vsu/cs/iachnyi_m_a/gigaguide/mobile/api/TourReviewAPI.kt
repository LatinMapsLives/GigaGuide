package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.review.CreateReviewDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.review.ReviewsDTO

interface TourReviewAPI {
    @GET("tours")
    fun getAll(@Header("Authorization") token: String, @Query("tourId") tourId: Int): Call<ReviewsDTO>

    @POST("tours")
    fun addReview(@Header("Authorization") token: String, @Body createReviewDTO: CreateReviewDTO, @Query("tourId") tourId: Int): Call<String>

    @DELETE("tours")
    fun deleteReview(@Header("Authorization") token: String, @Query("tourId") tourId: Int): Call<String>
}