package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore.DataStoreManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.MapPoint
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightOnMapInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.MapRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val mapRepository: MapRepository,
    private val sightRepository: SightRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    var center = GeoPoint(51.665859, 39.211282)
    var zoom = 16.0
    var sights = mutableStateListOf<SightOnMapInfo>()
    var needToLoad = true
    var loading by mutableStateOf(false)
    var selected by mutableStateOf(false)
    var selectedIndex = mutableLongStateOf(-1)

    var animateToCurrentLocationCallback: () -> Unit = {}
    var userLocation = mutableStateOf(MapPoint(0.0,0.0))

    var doLoop = false
    fun stopLoop(){
        doLoop = false
    }

    fun saveCurrentLocation(point: MapPoint){
        viewModelScope.launch {
            dataStoreManager.saveLastLocation(point)
            userLocation.value = point
        }
    }

    fun launchLoop(delayMillis: Long, callback: () -> Unit) {
        doLoop = true
        viewModelScope.launch () {
            while(doLoop){
                delay(delayMillis)
                callback.invoke()
            }
        }
    }

    fun loadSightsOnMap() {
        needToLoad = false
        viewModelScope.launch {
            userLocation.value = dataStoreManager.getLastLocation()
            loading = true
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

            loading = false
        }
    }
}