package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.util.GeoPoint
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightOnMapInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.MapRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository
import java.net.ConnectException
import java.net.SocketTimeoutException

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val mapRepository: MapRepository,
    private val sightRepository: SightRepository
) : ViewModel() {

    var center = GeoPoint(51.670859, 39.210282)
    var zoom = 16.0
    var sights = mutableStateListOf<SightOnMapInfo>()
    var loading = mutableStateOf(false)
    var selected = mutableStateOf(false)
    var selectedIndex = mutableLongStateOf(-1)

    fun loadSightsOnMap() {
        viewModelScope.launch {
            loading.value = true
            var loadedSights = try {
                withContext(Dispatchers.IO) {
                    sightRepository.getAllSightInfos()
                }
            } catch (e: SocketTimeoutException) {
                null
            } catch (e: ConnectException) {
                null
            }
            sights.clear()
            if (loadedSights != null) {
                for (sightDTO in loadedSights) {
                    var coords = try {
                        withContext(Dispatchers.IO) {
                            mapRepository.getCoordinatedOfSight(sightDTO.id)
                        }
                    } catch (e: SocketTimeoutException) {
                        null
                    } catch (e: ConnectException) {
                        null
                    }
                    if (coords != null)
                        sights.add(
                            SightOnMapInfo(
                                id = sightDTO.id,
                                name = sightDTO.name,
                                latitude = coords.latitude,
                                longitude = coords.longitude
                            )
                        )
                }
            }

            loading.value = false
        }
    }
}