package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.SightDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.TourDTO

interface TourAPI {
    @GET("tours/all")
    fun getAllTours(): Call<List<TourDTO>>

    @GET("tours")
    fun getTourById(@Query("id") id: Long): Call<TourDTO>
}