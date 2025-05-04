package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit

import kotlinx.coroutines.delay
import retrofit2.Response
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.UserAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.UserDataDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.UserRepository

class UserRepositoryRetrofit(private val userAPI: UserAPI) : UserRepository {
    override suspend fun getUserData(token: String): UserDataDTO? {
        delay(500)
        var response: Response<UserDataDTO> = userAPI.getUserData("Bearer " + token).execute()
        return if (response.isSuccessful) response.body() else null
    }

}