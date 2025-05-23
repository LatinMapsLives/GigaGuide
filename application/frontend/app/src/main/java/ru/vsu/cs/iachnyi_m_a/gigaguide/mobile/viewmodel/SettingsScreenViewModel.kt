package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore.DataStoreManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.user.UserDataDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.UserRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val userRepository: UserRepository
) : ViewModel() {

    val userData = mutableStateOf<UserDataDTO?>(null)
    val loading = mutableStateOf(false)

    fun discoverJWT() {
        viewModelScope.launch {
            var discoveredToken: String? = dataStoreManager.getJWT()
            if (userData.value != null) {
                Log.d("JWT", "DATA NOT NULL")
                if (discoveredToken == null) {
                    dataStoreManager.deleteJWT()
                    userData.value = null
                }
            } else {
                Log.d("JWT", "DATA NULL")
                if (discoveredToken != null) {
                    Log.d("JWT", "DISCOVERED TOKEN NOT NULL")
                    loading.value = true
                    var data: UserDataDTO? = ServerUtils.executeNetworkCall { userRepository.getUserData(discoveredToken) }
                    if (data == null) {
                        dataStoreManager.deleteJWT()
                    }
                    userData.value = data
                    loading.value = false
                }
            }
        }
    }
}