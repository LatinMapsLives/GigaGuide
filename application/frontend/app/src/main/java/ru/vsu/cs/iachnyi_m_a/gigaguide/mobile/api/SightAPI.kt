package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.PreviewSightDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.SightDTO

interface SightAPI {

    @GET("sights")
    fun getSightById(@Query("id") id: Long, @Query("language") lang: String): Call<SightDTO>

    @GET("sights/search")
    fun searchSights(@Query("name") name: String, @Query("language") lang: String): Call<List<PreviewSightDTO>>
}