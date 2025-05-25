package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore.DataStoreManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.CurrentThemeSettings
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils
import kotlin.random.Random

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val sightRepository: SightRepository, private val dataStoreManager: DataStoreManager) :
    ViewModel() {

    var closestTours = mutableStateListOf<SightTourThumbnail>()
    var loading = mutableStateOf<Boolean>(false)

    fun updateAppTheme() {
        viewModelScope.launch {
            var currTheme = dataStoreManager.getThemeSettings()
            CurrentThemeSettings.currentState = currTheme
        }
    }

    fun loadClosestTours() {
        loading.value = true;
        viewModelScope.launch {

            var sightInfos: List<SightInfo>? = ServerUtils.executeNetworkCall { sightRepository.getAllSightInfos() }

            closestTours.clear()
            if (sightInfos != null) {
                closestTours.addAll(sightInfos.map { si ->
                    SightTourThumbnail(
                        sightId = si.id,
                        name = si.name,
                        rating = Random(13471407342).nextInt(40, 51) / 10f,
                        proximity = Random(13471407342).nextInt(2, 20) / 10f,
                        imageLink = si.imageLink
                    )
                });
            }
            loading.value = false;
        }
    }

}