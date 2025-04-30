package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.RegisterUserDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.AuthRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.RegisterScreenError
import java.net.ConnectException
import java.net.SocketTimeoutException


@HiltViewModel
class RegisterScreenViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {
    var emailInput = mutableStateOf("")
    var userNameInput = mutableStateOf("")
    var passwordInput = mutableStateOf("")
    var passwordConfirmInput = mutableStateOf("")

    var registerScreenError = mutableStateOf(RegisterScreenError.NONE)
    var registerSuccess = mutableStateOf(false)
    var registerButtonActive = mutableStateOf(true)

    fun registerUser() {
        if (emailInput.value.trim().isEmpty() ||
            userNameInput.value.trim().isEmpty() ||
            passwordInput.value.trim().isEmpty() ||
            passwordConfirmInput.value.trim().isEmpty()
        ) {
            registerScreenError.value = RegisterScreenError.FIELDS_EMPTY
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput.value.trim()).matches()) {
            registerScreenError.value = RegisterScreenError.EMAIL_WRONG_FORMAT
        } else if (passwordInput.value != passwordConfirmInput.value) {
            registerScreenError.value = RegisterScreenError.PASSWORD_MISMATCH
        } else {
            registerScreenError.value = RegisterScreenError.NONE
            viewModelScope.launch {
                registerButtonActive.value = false
                var success = false
                withContext(Dispatchers.IO) {
                    success = try {
                        authRepository.register(
                            RegisterUserDTO(
                                email = emailInput.value.trim(),
                                username = userNameInput.value.trim(),
                                password = passwordInput.value,
                                confirmPassword = passwordConfirmInput.value
                            )
                        )
                    } catch (e: SocketTimeoutException) {
                        false
                    } catch (e: ConnectException) {
                        false
                    }
                }
                registerSuccess.value = success
                if (success) {
                    emailInput.value = ""
                    userNameInput.value = ""
                    passwordInput.value = ""
                    passwordConfirmInput.value = ""
                } else {
                    registerScreenError.value = RegisterScreenError.REGISTER_FAILED
                }
                registerButtonActive.value = true
            }
        }
    }
}