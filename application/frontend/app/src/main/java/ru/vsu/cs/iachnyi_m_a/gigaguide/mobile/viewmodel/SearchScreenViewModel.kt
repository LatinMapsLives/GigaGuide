package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
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
    var onlyTours by mutableStateOf(false)
    var sortingOptions by mutableStateOf(SortingOptions.NONE)
    var searchBarValue by mutableStateOf("")

    fun loadSearchResult() {

        loading = true

        viewModelScope.launch {
            sightResult.clear()
            tourResult.clear()

            var sightInfos: List<SightSearchResult>? =
                ServerUtils.executeNetworkCall { sightRepository.search(searchBarValue.trim()) }
            if (sightInfos != null) {
                if(sortingOptions == SortingOptions.RATING){
                    sightInfos = sightInfos.sortedBy { - it.rating }
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

            var tourInfos: List<SightTourThumbnail>? =
                ServerUtils.executeNetworkCall { tourRepository.searchTours(searchBarValue.trim()) }
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
            loading = false
        }
    }
}