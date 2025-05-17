package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.favorites.FavoritesDTO

interface FavoritesAPI {

    @GET("favorites")
    fun getAllFavoriteSights(@Header("Authorization") token: String): Call<FavoritesDTO>

    @POST("favorites/sights")
    fun addSightToFavorites(@Header("Authorization") token: String, @Query("sightId") sightId: Long): Call<String>

    @DELETE("favorites/sights")
    fun deleteSightFromFavorites(@Header("Authorization") token: String, @Query("sightId") sightId: Long): Call<String>

    @POST("favorites/tours")
    fun addTourToFavorites(@Header("Authorization") token: String, @Query("tourId") tourId: Long): Call<String>

    @DELETE("favorites/tours")
    fun deleteTourFromFavorites(@Header("Authorization") token: String, @Query("tourId") tourId: Long): Call<String>

}
