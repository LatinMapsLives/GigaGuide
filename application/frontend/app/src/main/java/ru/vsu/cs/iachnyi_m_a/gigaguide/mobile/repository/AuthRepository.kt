package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.JWTResponse
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.LoginRequestDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.RegisterUserDTO

interface AuthRepository {
    suspend fun register(registerUserDTO: RegisterUserDTO): Boolean
    suspend fun login(loginRequestDTO: LoginRequestDTO): String?
}