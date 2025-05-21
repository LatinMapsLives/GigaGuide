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
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(private var userRepository: UserRepository, private var dataStoreManager: DataStoreManager): ViewModel() {

    var newUsername by mutableStateOf("")
    var newEmail by mutableStateOf("")
    var oldPassword by mutableStateOf("")
    var newPassword by mutableStateOf("")

    var usernameChangeError by mutableStateOf(false)
    var usernameChangeSuccess by mutableStateOf(false)
    var emailChangeError by mutableStateOf(false)
    var emailChangeSuccess by mutableStateOf(false)
    var passwordChangeError by mutableStateOf(false)
    var passwordChangeSuccess by mutableStateOf(false)

    var usernameEditorOpen by mutableStateOf(false)
    var passwordEditorOpen by mutableStateOf(false)
    var emailEditorOpen by mutableStateOf(false)

    var currentUsername by mutableStateOf("")
    var currentEmail by mutableStateOf("")

    fun loadUserData(){
        viewModelScope.launch {
            if(dataStoreManager.getJWT() == null) return@launch
            var data: UserDataDTO? = ServerUtils.executeNetworkCall { userRepository.getUserData(dataStoreManager.getJWT()!!) }
            if(data != null){
                currentUsername = data.username
                currentEmail = data.email
            }
        }
    }

    fun updatePassword(){
        viewModelScope.launch {
            if(dataStoreManager.getJWT() == null) return@launch
            var success  = ServerUtils.executeNetworkCall { userRepository.updatePassword(dataStoreManager.getJWT()!!, oldPassword, newPassword) }
            if(success != null && success){
                passwordChangeSuccess = true
                passwordChangeError = false
                oldPassword = ""
                newPassword = ""
            } else {
                passwordChangeSuccess = false
                passwordChangeError = true
            }
        }
    }

    fun updateUsername(){
        viewModelScope.launch {
            if(dataStoreManager.getJWT() == null) return@launch
            var success  = ServerUtils.executeNetworkCall { userRepository.updateUsername(dataStoreManager.getJWT()!!, newUsername) }
            if(success != null && success){
                usernameChangeSuccess = true
                usernameChangeError = false
                newUsername = ""
            } else {
                usernameChangeSuccess = false
                usernameChangeError = true
            }
        }
    }

    fun updateEmail(){
        viewModelScope.launch {
            if(dataStoreManager.getJWT() == null) return@launch
            var success  = ServerUtils.executeNetworkCall { userRepository.updateEmail(dataStoreManager.getJWT()!!, newEmail) }
            if(success != null && success){
                emailChangeSuccess = true
                emailChangeError = false
                newEmail = ""
            } else {
                emailChangeSuccess = false
                emailChangeError = true
            }
        }
    }

}