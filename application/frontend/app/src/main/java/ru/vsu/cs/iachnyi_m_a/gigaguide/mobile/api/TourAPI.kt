package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.SightDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.TourDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.PreviewTourDTO

interface TourAPI {

    @GET("tours")
    fun getTourById(@Query("id") id: Long, @Query("language") language: String): Call<TourDTO>

    @GET("tours/search-filter")
    fun searchTours(@QueryMap params: Map<String, String>): Call<List<PreviewTourDTO>>
}