package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.user.UserDataDTO

interface UserRepository {
    suspend fun getUserData(token: String): UserDataDTO?
    suspend fun updateUsername(token: String, username: String): Boolean?
    suspend fun updateEmail(token: String, email: String): Boolean?
    suspend fun updatePassword(token: String, oldPassword: String, newPassword: String): Boolean?
}