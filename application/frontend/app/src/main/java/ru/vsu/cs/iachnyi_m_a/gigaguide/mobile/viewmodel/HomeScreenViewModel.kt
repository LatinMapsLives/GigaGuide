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
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.LocaleManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils
import kotlin.math.pow
import kotlin.math.sqrt

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val sightRepository: SightRepository,
    private val dataStoreManager: DataStoreManager
) :
    ViewModel() {

    var closestSights = mutableStateListOf<SightTourThumbnail>()
    var popularSights = mutableStateListOf<SightTourThumbnail>()
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
                ServerUtils.executeNetworkCall { sightRepository.search("", LocaleManager.currentLanguage) }

            closestSights.clear()
            popularSights.clear()
            if (sightInfos != null) {
                var currentLocation = dataStoreManager.getLastLocation()
                closestSights.addAll(sightInfos.map {
                    si ->

                    var proximity_km = proximityKm(si.latitude.toDouble(), si.longitude.toDouble(), currentLocation.latitude, currentLocation.longitude)

                    SightTourThumbnail(
                        isTour = false,
                        sightId = si.id.toLong(),
                        name = si.name,
                        rating = si.rating,
                        proximity = proximity_km,
                        imageLink = si.imageLink
                    )
                });

                closestSights.sortWith { s1, s2 -> s1.proximity.compareTo(s2.proximity) }

                popularSights.addAll(closestSights)
                popularSights.sortWith { s1, s2 -> s2.rating.compareTo(s1.rating) }
            }
            loading.value = false;
        }
    }

}

fun proximityKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float{
    var proximity_coords = sqrt(
        (lat1 - lat2).pow(2.0) + (lon1 - lon2).pow(
            2.0
        )
    )

    var proximity_km = ((proximity_coords * 100) * 10).toInt() / 10f

    return proximity_km
}