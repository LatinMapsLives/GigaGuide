package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit

import retrofit2.Response
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.UserAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.user.UpdateEmailDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.user.UpdatePasswordDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.user.UpdateUsernameDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.user.UserDataDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.UserRepository

class UserRepositoryRetrofit(private val userAPI: UserAPI) : UserRepository {
    override suspend fun getUserData(token: String): UserDataDTO? {
        var response: Response<UserDataDTO> = userAPI.getUserData("Bearer " + token).execute()
        return if (response.isSuccessful) response.body() else null
    }

    override suspend fun updateUsername(
        token: String,
        username: String
    ): Boolean? {
        var response: Response<String> = userAPI.updateUsername(
            "Bearer " + token,
            UpdateUsernameDTO(username)
        ).execute()
        return response.isSuccessful
    }

    override suspend fun updateEmail(token: String, email: String): Boolean? {
        var response: Response<String> = userAPI.updateUserEmail(
            "Bearer " + token,
            UpdateEmailDTO(email)
        ).execute()
        return response.isSuccessful
    }

    override suspend fun updatePassword(
        token: String,
        oldPassword: String,
        newPassword: String
    ): Boolean? {
        var response: Response<String> = userAPI.updatePassword(
            "Bearer " + token,
            UpdatePasswordDTO(oldPassword, newPassword)
        ).execute()
        return response.isSuccessful
    }

}