package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.CoordinatesDTO

interface MapAPI {
    @GET("sight")
    fun getSightCoordinates(@Query("id") id: Long): Call<CoordinatesDTO>

    @GET("moment")
    fun getMomentCoordinates(@Query("id") id: Long): Call<CoordinatesDTO>
}