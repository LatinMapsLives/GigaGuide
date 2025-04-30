package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit

import kotlinx.coroutines.delay
import retrofit2.Response
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.AuthAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.JWTResponse
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.LoginRequestDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.RegisterUserDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.AuthRepository

class AuthRepositoryRetrofit(private val authAPI: AuthAPI): AuthRepository {

    override suspend fun register(registerUserDTO: RegisterUserDTO): Boolean {
        var response: Response<String> = authAPI.registerUser(registerUserDTO).execute()
        return response.isSuccessful
    }

    override suspend fun login(loginRequestDTO: LoginRequestDTO): String? {
        var response: Response<JWTResponse> = authAPI.login(loginRequestDTO).execute()
        return if(response.isSuccessful){
            response.body().token
        } else {
            null
        }
    }

}