package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.MomentDTO

interface MomentAPI {
    @GET("sight")
    fun getAllMoments(@Query("sightId") sightId: Long, @Query("language") lang: String): Call<List<MomentDTO>>
}