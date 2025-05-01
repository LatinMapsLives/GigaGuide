package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api

import retrofit2.Call
import retrofit2.http.GET
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.MomentDTO

interface MomentAPI {
    @GET("all")
    fun getAllMoments(): Call<List<MomentDTO>>
}