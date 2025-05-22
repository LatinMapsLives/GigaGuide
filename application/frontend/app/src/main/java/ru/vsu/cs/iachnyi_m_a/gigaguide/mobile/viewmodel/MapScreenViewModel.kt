package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightOnMapInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.MapRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val mapRepository: MapRepository,
    private val sightRepository: SightRepository
) : ViewModel() {

    var center = GeoPoint(51.665859, 39.211282)
    var zoom = 16.0
    var sights = mutableStateListOf<SightOnMapInfo>()
    var needToLoad = true
    var loading = mutableStateOf(false)
    var selected = mutableStateOf(false)
    var selectedIndex = mutableLongStateOf(-1)

    fun loadSightsOnMap() {
        needToLoad = false
        viewModelScope.launch {
            loading.value = true
            var loadedSights = ServerUtils.executeNetworkCall { sightRepository.getAllSightInfos() }
            sights.clear()
            if (loadedSights != null) {
                for (sightInfo in loadedSights) {
                    var coords = ServerUtils.executeNetworkCall {  mapRepository.getCoordinatedOfSight(sightInfo.id)}
                    if (coords != null)
                        sights.add(
                            SightOnMapInfo(
                                id = sightInfo.id,
                                name = sightInfo.name,
                                latitude = coords.latitude,
                                longitude = coords.longitude,
                                sightInfo.imageLink
                            )
                        )
                }
            }

            loading.value = false
        }
    }
}