package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore.DataStoreManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightSearchResult
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.TourRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.LocaleManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils
import javax.inject.Inject
import kotlin.math.pow

enum class SortingOptions {
    NONE,
    RATING,
    REMOTENESS
}


@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val sightRepository: SightRepository,
    private val tourRepository: TourRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    var sightResult = mutableStateListOf<SightTourThumbnail>()
    var tourResult = mutableStateListOf<SightTourThumbnail>()
    var loading by mutableStateOf(false)

    var searchSights by mutableStateOf(true)
    var searchTours by mutableStateOf(true)
    var filterByDistance by mutableStateOf(false)
    var filterByDuration by mutableStateOf(false)
    var minDuration by mutableIntStateOf(0)
    var maxDuration by mutableIntStateOf(120)
    var minDistance by mutableDoubleStateOf(0.0)
    var maxDistance by mutableDoubleStateOf(100.0)

    var sortingOptions by mutableStateOf(SortingOptions.NONE)
    var searchBarValue by mutableStateOf("")

    fun loadSearchResult() {

        loading = true

        viewModelScope.launch {
            sightResult.clear()
            tourResult.clear()

            if(searchSights) {
                var sightInfos: List<SightSearchResult>? =
                    ServerUtils.executeNetworkCall {
                        sightRepository.search(
                            searchBarValue.trim(),
                            LocaleManager.currentLanguage
                        )
                    }
                if (sightInfos != null) {
                    if (sortingOptions == SortingOptions.RATING) {
                        sightInfos = sightInfos.sortedBy { -it.rating }
                    } else {
                        var userLat = dataStoreManager.getLastLocation().latitude
                        var userLon = dataStoreManager.getLastLocation().longitude
                        sightInfos = sightInfos.sortedBy {
                            var lat = it.latitude
                            var lon = it.longitude
                            (lat - userLat).pow(2) + (lon - userLon).pow(2)
                        }
                    }
                    sightResult.addAll(sightInfos.map {
                        SightTourThumbnail(
                            sightId = it.id.toLong(),
                            name = it.name,
                            rating = it.rating,
                            proximity = 0f,
                            imageLink = it.imageLink
                        )
                    });
                }
            }

            if(searchTours) {
                var tourInfos: List<SightTourThumbnail>? =
                    ServerUtils.executeNetworkCall {
                        tourRepository.searchFilterTours(
                            name = searchBarValue.trim(),
                            language = LocaleManager.currentLanguage,
                            minDuration = minDuration,
                            maxDuration = maxDuration,
                            minDistance = minDistance,
                            maxDistance = maxDistance
                        )
                    }
                if (tourInfos == null) Log.e("SEARCH", "NIGGA")
                if (tourInfos != null) {
                    tourResult.addAll(tourInfos.map { ti ->
                        SightTourThumbnail(
                            sightId = ti.sightId,
                            name = ti.name,
                            rating = ti.rating,
                            proximity = 0f,
                            imageLink = ti.imageLink
                        )
                    });
                }
            }

            loading = false
        }
    }
}