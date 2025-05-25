package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore.DataStoreManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightSearchResult
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.CurrentThemeSettings
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils
import kotlin.math.pow
import kotlin.math.sqrt

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val sightRepository: SightRepository,
    private val dataStoreManager: DataStoreManager
) :
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

            var sightInfos: List<SightSearchResult>? =
                ServerUtils.executeNetworkCall { sightRepository.search("") }

            closestTours.clear()
            if (sightInfos != null) {
                var currentLocation = dataStoreManager.getLastLocation()
                closestTours.addAll(sightInfos.map {

                        si ->
                    var proximity_coords = sqrt(
                        (si.latitude - currentLocation.latitude).pow(2.0) + (si.longitude - currentLocation.longitude).pow(
                            2.0
                        )
                    )

                    var proximity_km = ((proximity_coords * 100) * 10).toInt() / 10f

                    SightTourThumbnail(
                        sightId = si.id.toLong(),
                        name = si.name,
                        rating = 0f,
                        proximity = proximity_km,
                        imageLink = si.imageLink
                    )
                });

                closestTours.sortWith(Comparator<SightTourThumbnail> { s1, s2 -> s1.proximity.compareTo(s2.proximity) })
            }
            loading.value = false;
        }
    }

}