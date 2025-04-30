package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore.DataStoreManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.LoginRequestDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.AuthRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.LoginScreenError
import java.net.ConnectException
import java.net.SocketTimeoutException

@HiltViewModel
class LoginScreenViewModel @Inject constructor(private val dataStoreManager: DataStoreManager, private val authRepository: AuthRepository) : ViewModel() {

    var emailInput = mutableStateOf<String>("")
    var passwordInput = mutableStateOf<String>("")

    var error = mutableStateOf(LoginScreenError.NONE)
    var loginSuccess = mutableStateOf(false)
    var loginButtonActive = mutableStateOf(true)
    var popNavigationBackStackCallback : () -> Unit = {}

    fun loginUser(){
        if (emailInput.value.trim().isEmpty() ||
            passwordInput.value.trim().isEmpty()
        ) {
            error.value = LoginScreenError.FIELDS_EMPTY
        } else {
            error.value = LoginScreenError.NONE
            viewModelScope.launch {
                loginButtonActive.value = false
                var token: String? = null
                withContext(Dispatchers.IO) {
                    token = try {
                        authRepository.login(
                            LoginRequestDTO(
                                email = emailInput.value.trim(),
                                password = passwordInput.value
                            )
                        )
                    } catch (e: SocketTimeoutException) {
                        null
                    } catch (e: ConnectException) {
                        null
                    }
                }
                loginSuccess.value = token != null
                if (loginSuccess.value) {
                    emailInput.value = ""
                    passwordInput.value = ""
                    dataStoreManager.saveJWT(token!!)
                    popNavigationBackStackCallback.invoke()
                } else {
                    error.value = LoginScreenError.LOGIN_ERROR
                }
                loginButtonActive.value = true
            }
        }
    }

}