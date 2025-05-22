package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore.DataStoreManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.user.UserDataDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.UserRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.Pancake
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.validator.EmailValidator
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.validator.UsernameValidator

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(private var userRepository: UserRepository, private var dataStoreManager: DataStoreManager): ViewModel() {

    var newUsername by mutableStateOf("")
    var newEmail by mutableStateOf("")
    var oldPassword by mutableStateOf("")
    var newPassword by mutableStateOf("")

    var usernameChangeError by mutableStateOf("")
    var emailChangeError by mutableStateOf("")
    var passwordChangeError by mutableStateOf("")

    var usernameEditorOpen by mutableStateOf(false)
    var passwordEditorOpen by mutableStateOf(false)
    var emailEditorOpen by mutableStateOf(false)

    var currentUsername by mutableStateOf("")
    var currentEmail by mutableStateOf("")
    var loading by mutableStateOf(false)

    fun loadUserData(){
        viewModelScope.launch {
            loading = true
            if(dataStoreManager.getJWT() == null) {
                loading = false
                return@launch
            }
            var data: UserDataDTO? = ServerUtils.executeNetworkCall { userRepository.getUserData(dataStoreManager.getJWT()!!) }
            if(data != null){
                currentUsername = data.username
                currentEmail = data.email
            }
            loading = false
        }
    }

    fun updatePassword(){
        viewModelScope.launch {
            loading = true
            if(dataStoreManager.getJWT() == null) {
                loading = false
                return@launch
            }
            var success  = ServerUtils.executeNetworkCall { userRepository.updatePassword(dataStoreManager.getJWT()!!, oldPassword, newPassword) }
            if(success != null && success){
                Pancake.success("Пароль успешно обновлён")
                passwordChangeError = ""
                oldPassword = ""
                newPassword = ""
                loadUserData()
            } else {
                passwordChangeError = "Ошибка обновления пароля"
            }
            loading = false
        }
    }

    fun updateUsername(){
        if(!UsernameValidator().validate(newUsername)){
            usernameChangeError = "Неверный формат имени"
            return
        }
        loading = true
        viewModelScope.launch {
            if(dataStoreManager.getJWT() == null){
                loading = false
                return@launch
            }
            var success  = ServerUtils.executeNetworkCall { userRepository.updateUsername(dataStoreManager.getJWT()!!, newUsername) }
            if(success != null && success){
                Pancake.success("Имя успешно обновлено")
                usernameChangeError = ""
                newUsername = ""
                loadUserData()
            } else {
                usernameChangeError = "Ошибка обновления имени"
            }
        }
        loading = false
    }

    fun updateEmail(){
        if(!EmailValidator().validate(newEmail)){
            emailChangeError = "Неверный формат почты"
            return
        }
        loading = true
        viewModelScope.launch {
            if(dataStoreManager.getJWT() == null) {
                loading = false
                return@launch
            }
            var success  = ServerUtils.executeNetworkCall { userRepository.updateEmail(dataStoreManager.getJWT()!!, newEmail) }
            if(success != null && success){
                Pancake.success("Почта успешно обновлена")
                emailChangeError = ""
                newEmail = ""
                loadUserData()
            } else {
                emailChangeError = "Ошибка обновления почты"
            }
        }
        loading = false
    }

    fun logout(afterErasingJWT:  () -> Unit){
        viewModelScope.launch {
            dataStoreManager.deleteJWT()
            afterErasingJWT.invoke()
        }
    }

}