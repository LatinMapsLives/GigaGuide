package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.UserDataDTO

interface UserAPI {
    @GET("/")
    fun getUserData(@Header("Authorization") token: String): Call<UserDataDTO>
}