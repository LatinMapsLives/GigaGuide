package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.UserDataDTO

interface UserRepository {
    suspend fun getUserData(token: String): UserDataDTO?
}