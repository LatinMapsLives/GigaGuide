package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
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

    var usernameChangeError by mutableIntStateOf(R.string.empty)
    var emailChangeError by mutableIntStateOf(R.string.empty)
    var passwordChangeError by mutableIntStateOf(R.string.empty)

    var usernameEditorOpen by mutableStateOf(false)
    var passwordEditorOpen by mutableStateOf(false)
    var emailEditorOpen by mutableStateOf(false)

    var currentUsername by mutableStateOf("")
    var currentEmail by mutableStateOf("")
    var loading by mutableStateOf(false)

    var usernameSuccess = ""
    var passwordSuccess = ""
    var emailSuccess = ""

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
        if(oldPassword.trim().isEmpty() || newPassword.trim().isEmpty()){
            passwordChangeError = R.string.profile_screen_error_fill_both_fields
            return
        }
        viewModelScope.launch {
            if(dataStoreManager.getJWT() == null) {
                loading = false
                return@launch
            }
            loading = true
            var success  = ServerUtils.executeNetworkCall { userRepository.updatePassword(dataStoreManager.getJWT()!!, oldPassword.trim(), newPassword.trim()) }
            if(success != null && success){
                Pancake.success(passwordSuccess)
                passwordChangeError = R.string.empty
                oldPassword = ""
                newPassword = ""
                loadUserData()
            } else {
                passwordChangeError = R.string.profile_screen_error_password_change_error
            }
            loading = false
        }
    }

    fun updateUsername(){
        if(newUsername.trim().isEmpty()){
            usernameChangeError = R.string.profile_screen_error_fill_in_the_field
            return
        }
        if(newUsername.trim() == currentUsername){
            usernameChangeError = R.string.profile_screen_error_names_must_differ
            return
        }
        if(!UsernameValidator().validate(newUsername)){
            usernameChangeError = R.string.profile_screen_error_wrong_username_format
            return
        }
        loading = true
        viewModelScope.launch {
            if(dataStoreManager.getJWT() == null){
                loading = false
                return@launch
            }
            var success  = ServerUtils.executeNetworkCall { userRepository.updateUsername(dataStoreManager.getJWT()!!, newUsername.trim()) }
            if(success != null && success){
                Pancake.success(usernameSuccess)
                usernameChangeError = R.string.empty
                newUsername = ""
                loadUserData()
            } else {
                usernameChangeError = R.string.profile_screen_error_username_update_error
            }
        }
        loading = false
    }

    fun updateEmail(){
        if(newEmail.trim().isEmpty()){
            emailChangeError = R.string.profile_screen_error_fill_in_the_field
            return
        }
        if(newEmail.trim() == currentEmail){
            emailChangeError = R.string.profile_screen_error_email_must_differ
            return
        }
        if(!EmailValidator().validate(newEmail)){
            emailChangeError = R.string.profile_screen_error_wrong_email_fomat
            return
        }
        loading = true
        viewModelScope.launch {
            if(dataStoreManager.getJWT() == null) {
                loading = false
                return@launch
            }
            var success  = ServerUtils.executeNetworkCall { userRepository.updateEmail(dataStoreManager.getJWT()!!, newEmail.trim()) }
            if(success != null && success){
                Pancake.success(emailSuccess)
                emailChangeError = R.string.empty
                newEmail = ""
                loadUserData()
            } else {
                emailChangeError = R.string.profile_screen_error_email_update_error
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