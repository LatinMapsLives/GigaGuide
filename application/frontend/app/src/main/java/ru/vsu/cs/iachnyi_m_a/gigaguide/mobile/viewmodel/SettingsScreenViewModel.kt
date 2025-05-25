package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore.DataStoreManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.user.UserDataDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.UserRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.CurrentThemeSettings
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.LocaleManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ThemeSettings
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val userRepository: UserRepository
) : ViewModel() {

    val userData = mutableStateOf<UserDataDTO?>(null)
    val loading = mutableStateOf(false)
    var themeSetting by mutableStateOf(ThemeSettings.AS_DEVICE)
    var currentLanguage by mutableStateOf("en")

    fun loadSettings(){
        viewModelScope.launch {
            themeSetting = dataStoreManager.getThemeSettings()
            currentLanguage = dataStoreManager.getCurrentLanguage()
        }
    }

    fun updateAppLanguage() {
        viewModelScope.launch {
            dataStoreManager.setLanguage(currentLanguage)
            LocaleManager.currentLanguage = dataStoreManager.getCurrentLanguage()
        }
    }

    fun updateThemeSettings() {
        viewModelScope.launch {
            dataStoreManager.setThemeSettings(themeSetting)
            CurrentThemeSettings.currentState = themeSetting
        }
    }

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