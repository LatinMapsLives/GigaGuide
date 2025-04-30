package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.JWTResponse
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.LoginRequestDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.RegisterUserDTO

interface AuthAPI {
    @POST("register")
    fun registerUser(@Body registerUserDTO: RegisterUserDTO): Call<String>

    @POST("login")
    fun login(@Body loginRequestDTO: LoginRequestDTO): Call<JWTResponse>
}