package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.user.UpdateEmailDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.user.UpdatePasswordDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.user.UpdateUsernameDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.user.UserDataDTO

interface UserAPI {
    @GET("user")
    fun getUserData(@Header("Authorization") token: String): Call<UserDataDTO>
    @PUT("user")
    fun updateUsername(@Header("Authorization") token: String, updateUsernameDTO: UpdateUsernameDTO): Call<String>
    @PUT("user")
    fun updateUserEmail(@Header("Authorization") token: String, updateEmailDTO: UpdateEmailDTO): Call<String>
    @PUT("user")
    fun updatePassword(@Header("Authorization") token: String, updatePasswordDTO: UpdatePasswordDTO): Call<String>
}